const DATE = {
    setDateFormat(dateStr) {
        let date = new Date(Date.parse(dateStr))
        let year = date.getFullYear()
        let month = date.getMonth() + 1
        let day = date.getDate()
        return `${year}-${month}-${day}`
    },
    setDetailDateFormat(dateStr) {
        let date = new Date(Date.parse(dateStr));
        return `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}` + this.setDateFormat(dateStr)
    }
}