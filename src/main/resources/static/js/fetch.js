const csrfHeader = () => {
    const t = document.querySelector('meta[name="_csrf"]')?.content;
    const h = document.querySelector('meta[name="_csrf_header"]')?.content || 'X-CSRF-TOKEN';
    return t ? {[h]: t} : {};
};

const FETCH = {
    async get(url, callbackFunc = null) {
        try {
            const res = await fetch(url);
            const json = await res.json().catch(() => null); // 실패 시 null
            return callbackFunc ? callbackFunc(json) : json;
        } catch (e) {
            console.error("GET 요청 실패:", e);
            return null;
        }
    },

    async post(url, requestBody, callbackFunc = null) {
        try {
            const res = await fetch(url, {
                method: "POST",
                headers: {'Content-Type': 'application/json; charset=utf-8', ...csrfHeader()},
                body: JSON.stringify(requestBody)
            });
            return callbackFunc ? callbackFunc(res) : res;
        } catch (e) {
            console.error("POST 요청 실패:", e);
            return null;
        }
    },

    async put(url, requestBody) {
        try {
            return await fetch(url, {
                method: "PUT",
                headers: {'Content-Type': 'application/json; charset=utf-8'},
                body: JSON.stringify(requestBody)
            });
        } catch (e) {
            console.error("PUT 요청 실패:", e);
            return null;
        }
    },

    async delete(url) {
        try {
            return await fetch(url, {method: "DELETE"});
        } catch (e) {
            console.error("DELETE 요청 실패:", e);
            return null;
        }
    },

    async patch(url, requestBody = null) {
        try {
            return await fetch(url, {
                method: "PATCH",
                headers: {'Content-Type': 'application/json; charset=utf-8'},
                body: JSON.stringify(requestBody)
            });
        } catch (e) {
            console.error("PATCH 요청 실패:", e);
            return null;
        }
    }
};
