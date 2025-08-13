// Toast Factory (no dependency, no external CSS)
// Usage:
//   const toast = Toast({ position:'top-right', duration:2200, max:5 });
//   toast.success('완료');
//   toast.error('에러');
//   toast.show('커스텀', { type:'warning', dismissible:true, progress:true });

const Toast = (initOptions = {}) => {
    // ---- defaults per instance ----
    let cfg = {
        position: 'top-right',   // 'top-right' | 'top-left' | 'bottom-right' | 'bottom-left' | 'top-center' | 'bottom-center'
        duration: 2200,
        max: 5,                  // simultaneous max
        zIndex: 9999,
        gap: 8,                  // container gap
        ...initOptions
    };

    // ---- shared style (inject once per document) ----
    if (!document.getElementById('_toast_factory_style_')) {
        const css = `
      :root { --toast-radius:12px; --toast-shadow:0 8px 24px rgba(0,0,0,.18); }
      .tf-container{position:fixed;z-index:${cfg.zIndex};display:flex;flex-direction:column;gap:${cfg.gap}px;pointer-events:none}
      .tf-container.top-right{top:20px;right:20px;align-items:flex-end}
      .tf-container.top-left{top:20px;left:20px;align-items:flex-start}
      .tf-container.bottom-right{bottom:20px;right:20px;align-items:flex-end}
      .tf-container.bottom-left{bottom:20px;left:20px;align-items:flex-start}
      .tf-container.top-center{top:20px;left:50%;transform:translateX(-50%);align-items:center}
      .tf-container.bottom-center{bottom:20px;left:50%;transform:translateX(-50%);align-items:center}

      .tf{pointer-events:auto;display:flex;flex-direction:column;gap:6px;min-width:240px;max-width:420px;
          padding:12px 14px;border-radius:var(--toast-radius);box-shadow:var(--toast-shadow);
          color:#fff;background:#333;opacity:0;transform:translateY(-10px);transition:opacity .22s, transform .22s;}
      .tf.show{opacity:1;transform:translateY(0)}
      .tf-body{display:flex;gap:10px;align-items:center}
      .tf-icon { font-size: 16px; line-height: 1; display:flex; align-items:center; }
      .tf-icon i { font-size: 1em; }

      .tf-msg{flex:1;font-size:14px;line-height:1.35}
      .tf-close{background:transparent;border:0;color:#fff;opacity:.85;cursor:pointer;font-size:18px;line-height:1;padding:0 2px}
      .tf-close:hover{opacity:1}

      .tf.success{background:#16a34a}
      .tf.error{background:#dc2626}
      .tf.info{background:#2d6cdf}
      .tf.warning{background:#d97706}

      .tf-progress{height:3px;background:rgba(255,255,255,.35);border-radius:999px;overflow:hidden}
      .tf-progress > span{display:block;height:100%;background:rgba(255,255,255,.92);width:100%;transform-origin:left;transition:width linear}
    `;
        const style = document.createElement('style');
        style.id = '_toast_factory_style_';
        style.textContent = css;
        document.head.appendChild(style);
    }

    // ---- instance state ----
    let container = null;
    let queue = [];
    const active = new Set();

    const ensureContainer = () => {
        if (container && container.isConnected) return container;
        container = document.createElement('div');
        container.className = `tf-container ${cfg.position}`;
        container.style.zIndex = String(cfg.zIndex);
        document.body.appendChild(container);
        return container;
    };

    const iconClassFor = (type) => ({
        success: 'fa-solid fa-circle-check',
        error: 'fa-solid fa-circle-xmark',
        warning: 'fa-solid fa-triangle-exclamation',
        info: 'fa-solid fa-circle-info'
    }[type] || 'fa-solid fa-circle-info');

    const mount = (opts) => {
        const {
            message,
            type = 'info',
            duration = cfg.duration,
            dismissible = false,
            progress = false,
            onClick,
            onClose
        } = opts;

        const wrap = document.createElement('div');
        wrap.className = `tf ${type}`;
        const body = document.createElement('div');
        body.className = 'tf-body';

        const icon = document.createElement('div');
        icon.className = 'tf-icon';
        const i = document.createElement('i');
        i.className = iconClassFor(type);
        icon.appendChild(i);

        const msg = document.createElement('div');
        msg.className = 'tf-msg';
        msg.textContent = String(message ?? '');

        const btnClose = document.createElement('button');
        btnClose.className = 'tf-close';
        btnClose.innerHTML = '&times;';
        if (!dismissible) btnClose.style.display = 'none';

        body.append(icon, msg, btnClose);
        wrap.appendChild(body);

        let progBar, progFill, start, rafId;
        let remaining = duration;

        const startProgress = () => {
            if (!progress) return;
            progBar = document.createElement('div');
            progBar.className = 'tf-progress';
            progFill = document.createElement('span');
            progBar.appendChild(progFill);
            wrap.appendChild(progBar);

            start = performance.now();
            const tick = (t) => {
                const elapsed = t - start;
                const p = Math.max(0, 1 - elapsed / duration);
                progFill.style.width = `${p * 100}%`;
                if (elapsed < duration) rafId = requestAnimationFrame(tick);
            };
            rafId = requestAnimationFrame(tick);
        };

        ensureContainer().appendChild(wrap);
        requestAnimationFrame(() => wrap.classList.add('show'));
        startProgress();

        const close = (reason = 'timeout') => {
            if (!wrap.isConnected) return;
            if (rafId) cancelAnimationFrame(rafId);
            wrap.classList.remove('show');
            wrap.addEventListener('transitionend', () => {
                wrap.remove();
                active.delete(wrap);
                if (typeof onClose === 'function') onClose(reason);
                const next = queue.shift();
                if (next) show(next.message, next);
            }, {once: true});
        };

        let timer = setTimeout(close, duration);

        // pause on hover
        wrap.addEventListener('mouseenter', () => {
            const now = performance.now();
            if (start) remaining = Math.max(0, duration - (now - start));
            clearTimeout(timer);
            if (rafId) cancelAnimationFrame(rafId);
        });
        wrap.addEventListener('mouseleave', () => {
            if (progress) {
                start = performance.now();
                rafId = requestAnimationFrame(function tick(t) {
                    const elapsed = t - start;
                    const p = Math.max(0, 1 - elapsed / remaining);
                    progFill.style.width = `${p * 100}%`;
                    if (elapsed < remaining) rafId = requestAnimationFrame(tick);
                });
            }
            timer = setTimeout(close, remaining);
        });

        if (dismissible) btnClose.addEventListener('click', () => close('manual'));
        if (typeof onClick === 'function') {
            wrap.addEventListener('click', (e) => {
                if (e.target === btnClose) return;
                onClick(e);
            });
        }

        active.add(wrap);
        return {close: () => close('api')};
    };

    const show = (message, options = {}) => {
        const opts = {...options, message};
        if (active.size >= cfg.max) {
            queue.push(opts);
            return {
                close: () => {
                }
            };
        }
        return mount(opts);
    };

    // ---- public instance API ----
    const api = {
        show, // toast.show('메시지', { type:'success', ... })
        success: (m, d) => show(m, {type: 'success', duration: d}),
        error: (m, d) => show(m, {type: 'error', duration: d}),
        info: (m, d) => show(m, {type: 'info', duration: d}),
        warning: (m, d) => show(m, {type: 'warning', duration: d}),
        configure(next = {}) {
            cfg = {...cfg, ...next};
            if (container) container.className = `tf-container ${cfg.position}`;
            if (container) container.style.zIndex = String(cfg.zIndex);
        },
        closeAll() {
            queue = [];
            [...active].forEach(el => {
                el.classList.remove('show');
                el.addEventListener('transitionend', () => el.remove(), {once: true});
                active.delete(el);
            });
        }
    };

    return api;
};

window.Toast = window.Toast || Toast;
