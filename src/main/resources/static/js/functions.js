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


/**
 * URL 이동을 위한 공통 함수
 * @param {string} url - 이동할 URL
 * @param {Object} params - (선택) 쿼리 파라미터 객체 (ex. {id: 1, name: "test"})
 * @param {boolean} replace - (선택) true면 현재 히스토리의 URL을 대체하고, false면 새로운 기록을 남김 (기본값: false)
 */

function moveUrl(url, params = {}, replace = false) {
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
function getSiteId (defaultUrl) {
    if(!defaultUrl) {
		console.log("세션 Item 키값 없음");
	}
	
	console.log(defaultUrl);
	
	let urlSegments = defaultUrl.split('/');

	console.log("segments ::" +  urlSegments[1]);
	// siteId 부분만 추출
	if(urlSegments.length > 1) {
		siteId = urlSegments[1];
	}else {
		console.log("Invalid URL format");
	}
}

document.addEventListener("DOMContentLoaded", function () {
    sideBar.setting();
    
})