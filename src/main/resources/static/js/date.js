const DATE = {
    setDateFormat(dateStr) {
        let date = new Date(Date.parse(dateStr))
        let year = date.getFullYear()
        let month = String(date.getMonth() + 1).padStart(2, '0'); // 두 자리로 포맷
    	let day = String(date.getDate()).padStart(2, '0'); // 두 자리로 포맷
        return `${year}-${month}-${day}`
    },
    setDetailDateFormat(dateStr) {
        let date = new Date(Date.parse(dateStr));
        return `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}` + this.setDateFormat(dateStr)
    }
}