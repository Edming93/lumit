const FETCH = {
    /**
     *
     * @param {string} url
     * @param {function} callbackFunc
     * @returns any
     */
    async get(url, callbackFunc = null) {
        const response = await fetch(url).then(res =>
            res).then(res => {
                try {
                    return res.json()
                } catch (e) {
                    return res;
                }
            }
        )
        if (callbackFunc != null) {
            return await callbackFunc(response)
        }
        return response;
    },
    /**
     *
     * @param {string} url
     * @param requestBody
     * @param {function} callbackFunc
     * @returns {Promise<Response>}
     */
    async post(url, requestBody, callbackFunc = null) {
        const response = await fetch(url, {
            method: "POST",
            headers: {'Content-Type': 'application/json; charset=utf-8', 'dataType': 'json'},
            body: JSON.stringify(requestBody)
        })
        if (callbackFunc != null) {
            return callbackFunc(response);
        }
        return response
    },
    async put(url, requestBody) {
        return await fetch(url, {
            method: "PUT",
            headers: {'Content-Type': 'application/json; charset=utf-8', 'dataType': 'json'},
            body: JSON.stringify(requestBody)
        })
    },
    async delete(url) {
        return await fetch(url, {method: "DELETE"})
    },
    async patch(url, requestBody = null) {
        return await fetch(url, {
            method: "PATCH",
            headers: {'Content-Type': 'application/json; charset=utf-8', 'dataType': 'json'},
            body: JSON.stringify(requestBody)
        }).then(res => res).then(res => res)
    }
}