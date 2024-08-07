let sideBar = {
    getState() {
        let state = cookie.getCookie("sb_hidden");
        if (!state) {
            cookie.setCookie("sb_hidden", "F", 14);
            state = cookie.getCookie("sb_hidden");
        }
        return state
    },
    setting() {
        let state = this.getState();
        const sidebar = document.getElementById("sidebar");
        if (state == "F") {
            sidebar.classList.remove("sb_hidden");
            this.showMenuText();
        } else {
            sidebar.classList.add("sb_hidden");
            this.hideMenuText();
        }
        this.buttonControl();
    },
    open() {
        if (this.getState() == "F") {
            return;
        }
        const sidebar = document.getElementById("sidebar");
        sidebar.classList.remove("sb_hidden");
        this.showMenuText();
    },
    close() {
        if (this.getState() == "F") {
            return;
        }
        const sidebar = document.getElementById("sidebar");
        sidebar.classList.add("sb_hidden");
        this.hideMenuText();
    },
    toggleSidebar() {
        let state = this.getState();
        const sidebar = document.getElementById("sidebar");
        if (state == "T") {
            sidebar.classList.remove("sb_hidden");
            this.showMenuText();
            cookie.deleteCookie("sb_hidden");
            cookie.setCookie("sb_hidden", "F", 14);
        } else {
            sidebar.classList.add("sb_hidden");
            this.hideMenuText();
            cookie.deleteCookie("sb_hidden");
            cookie.setCookie("sb_hidden", "T", 14);
        }
        this.buttonControl();
    },
    buttonControl() {
        const toggleBtn = document.getElementById("toggleBtnId");
        if (this.getState() == "T") {
            toggleBtn.classList.add("rotateArrow");
        } else {
            toggleBtn.classList.remove("rotateArrow")
        }
    },
    showMenuText() {
        const menuList = document.getElementsByClassName("wide");
        setTimeout(function () {
            for (let i = 0; i < menuList.length; i++) {
                menuList.item(i).classList.remove('hidden');
            }
        }, 170)
    },
    hideMenuText() {
        const menuList = document.getElementsByClassName("wide");
        for (let i = 0; i < menuList.length; i++) {
            menuList.item(i).classList.add('hidden');
        }
    }
}

let cookie = {
    setCookie(name, value, exp) {
        var date = new Date();
        date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
        document.cookie = `${name}=${value};expires=${date.toUTCString()};path=/;`
    },
    getCookie(name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : null;
    },
    deleteCookie(name) {
        document.cookie = name + "=; expires=Thu, 01 Jan 1999 00:00:10 GMT;";
    }
}

let formControl = {
    phoneNumCorrection() {
        const phoneInput = document.getElementById("phone");

    }
}

document.addEventListener("DOMContentLoaded", function () {
    sideBar.setting();
})