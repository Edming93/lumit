function hideMenuText() {
    const menuList = document.getElementsByClassName("wide");
    for (let i = 0; i < menuList.length; i++) {
        menuList.item(i).classList.add('hidden');
    }
}

function showMenuText() {
    const menuList = document.getElementsByClassName("wide");
    console.log(menuList.length);
    for (let i = 0; i < menuList.length; i++) {
        menuList.item(i).classList.remove('hidden');
    }
}

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
            showMenuText();
        } else {
            sidebar.classList.add("sb_hidden");
            hideMenuText();
        }
    },
    toggleSidebar() {
        let state = this.getState();
        const sidebar = document.getElementById("sidebar");
        if (state == "T") {
            sidebar.classList.remove("sb_hidden");
            showMenuText();
            cookie.deleteCookie("sb_hidden");
            cookie.setCookie("sb_hidden", "F", 14);
        } else {
            sidebar.classList.add("sb_hidden");
            hideMenuText();
            cookie.deleteCookie("sb_hidden");
            cookie.setCookie("sb_hidden", "T", 14);
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
        console.log(value);
        return value ? value[2] : null;
    },
    deleteCookie(name) {
        document.cookie = name + "=; expires=Thu, 01 Jan 1999 00:00:10 GMT;";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    console.log(cookie.getCookie("sb_hidden"));
    sideBar.setting();
})