/**
 * admin nav-tab 조작 부분
 * @type {{getState(): *|string, showMenuText(): void, buttonControl(): void, toggleSidebar(): void, hideMenuText(): void, close(): void, open(): void, setting(): void}}
 */

let sideBar = {
    /**
     * cookie에 sb_hidden 정보가 없다면 새로 설정해주고 있다면 상태를 가져온다.
     * @returns {*|string}
     */
    getState() {
        let state = cookie.getCookie("sb_hidden");
        if (!state) {
            cookie.setCookie("sb_hidden", "F", 14);
            state = cookie.getCookie("sb_hidden");
        }
        return state
    },
    /**
     * 쿠키에 세팅된 값으로 nav-bar를 작동시킨다.
     */
    setting() {
        if (this.getState() == "T") {
            this.close();
        } else {
            this.open();
        }
    },
    /**
     * sb_hidden이 T일 때, mouseenter되면 작동
     */
    open() {
        if (this.getState() == "F") {
            return;
        }
        const sidebar = document.getElementById("sidebar");
        sidebar.classList.remove("sb_hidden");
        this.showMenuText()
    },
    /**
     * sb_hidden이 T일 때, mouseleave되면 작동
     */
    close() {
        if (this.getState() == "F") {
            return;
        }
        const sidebar = document.getElementById("sidebar");
        sidebar.classList.add("sb_hidden");
        this.hideMenuText();
    },
    /**
     * 토글 버튼이 눌렸을 때 작동
     */
    toggleSidebar() {
        let state = this.getState();
        const sidebar = document.getElementById("sidebar");
        if (state == "T") {
            sidebar.classList.remove("sb_hidden");
            this.showMenuText()
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
    /**
     * 토글 버튼 방향 바꾸기
     */
    buttonControl() {
        const toggleBtn = document.getElementById("toggleBtnId");
        if (this.getState() == "T") {
            toggleBtn.classList.add("rotateArrow");
        } else {
            toggleBtn.classList.remove("rotateArrow")
        }
    },
    /**
     * 메뉴 텍스트 보여주기
     */
    showMenuText() {
        const menuList = document.getElementsByClassName("wide");
        for (let i = 0; i < menuList.length; i++) {
            menuList.item(i).classList.remove('hidden');
        }
    },
    /**
     * 메뉴 텍스트 숨기기
     */
    hideMenuText() {
        const menuList = document.getElementsByClassName("wide");
        for (let i = 0; i < menuList.length; i++) {
            menuList.item(i).classList.add('hidden');
        }
    }
}

let cookie = {
    setCookie(name, value, exp) {
        let date = new Date();
        date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
        document.cookie = `${name}=${value};expires=${date.toUTCString()};path=/;`
    },
    getCookie(name) {
        let value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
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

function resultProcessing(outside, inside, state) {
    inside.innerHTML = "";
    if (state === "fail") {
        inside.innerHTML += "<i class=\"fa-solid fa-x\"></i>"
        outside.parentElement.classList.remove("success")
        outside.parentElement.classList.add("failed")
        inside.classList.remove("resultBoxSuccess")
        inside.classList.add("resultBoxFail")
    } else if (state === "reset") {
        inside.innerHTML = ""
        outside.parentElement.classList.remove("failed")
        outside.parentElement.classList.remove("success")
        inside.classList.remove("resultBoxFail")
        inside.classList.remove("resultBoxSuccess")
    } else {
        inside.innerHTML += "<i class=\"fa-solid fa-check\"></i>"
        outside.parentElement.classList.remove("failed")
        outside.parentElement.classList.add("success")
        inside.classList.remove("resultBoxFail")
        inside.classList.add("resultBoxSuccess")
    }
}


let validate = {
    /**
     *
     * @param id : HTMLInputElement 중복체크할 input element
     * @param dest : HTMLElement 체크 결과가 들어갈 element
     * @param text : HTMLElement 체크 결과 메시지가 들어갈 element
     * @returns {boolean}
     *
     */
    idValidate(id, dest) {
        const regex = /^[0-9a-z]{4,16}$/;
        if (id.value == "") {
            resultProcessing(id, dest, "reset")
            return true;
        } else if (!regex.test(id.value)) {
            resultProcessing(id, dest, "fail")
            return false;
        } else {
            resultProcessing(id, dest, "success")
            return true;
        }
    },
    idDuplicateCheck(id, dest, text) {
        if (this.idValidate(id, dest) === false) {
            text.innerText = "영문 소문자/숫자로 이루어진 4~16자를 입력해주세요."
            return false;
        }

        let xhr = new XMLHttpRequest();
        xhr.responseType = "json"
        xhr.open("GET", `/api/opened/idCheck?id=${id.value}`, true);
        xhr.send();
        xhr.onreadystatechange = () => {
            if (xhr.readyState === xhr.DONE) {
                const res = xhr.response;
                if (res.data === true) {
                    resultProcessing(id, dest, "success")
                } else if (res.data === false) {
                    resultProcessing(id, dest, "fail")
                }
                text.innerText = res.msg
                return;
            }
        }
    }
}

/**
 * URL 이동을 위한 공통 함수
 * @param {string} url - 이동할 URL
 * @param {Object} params - (선택) 쿼리 파라미터 객체 (ex. {id: 1, name: "test"})
 * @param {boolean} replace - (선택) true면 현재 히스토리의 URL을 대체하고, false면 새로운 기록을 남김 (기본값: false)
 */

function moveUrl(url, params = {}, replace = false) {
    console.log(url)
    // 쿼리 파라미터가 있는 경우 처리
    if (params && Object.keys(params).length > 0) {
        const queryString = new URLSearchParams(params).toString();
        url += `?${queryString}`;
    }

    // replace가 true이면 현재 URL을 대체, false이면 새로운 URL로 이동
    if (replace) {
        window.location.replace(url);
    } else {
        window.location.assign(url);
    }
};

let siteId = '';

// siteId 가져오기
function getSiteId(defaultUrl) {
    if (!defaultUrl) {
        console.log("세션 Item 키값 없음");
    }

    console.log(defaultUrl);

    let urlSegments = defaultUrl.split('/');

    console.log("segments ::" + urlSegments[1]);
    // siteId 부분만 추출
    if (urlSegments.length > 1) {
        siteId = urlSegments[1];
    } else {
        console.log("Invalid URL format");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    sideBar.setting();

})