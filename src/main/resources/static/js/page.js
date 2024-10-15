const PAGE = {
    /**
     *
     * @param {number} totalPageCount 전체 페이지의 수
     * @param {number} pageNo 0부터 시작하는 페이지의 번호
     * @param {number} totalElementCount 받아온 게시글의 수
     * @param {string} fn 콜백 펑션의 함수 이름
     * @returns {boolean}
     */
    paging(totalPageCount, pageNo, totalElementCount, fn) {
        if (totalElementCount == 0) {
            document.querySelector("#pagingArea").innerHTML = "";
            return false;
        }
        /**
         * 한번에 볼 수 있는 페이지 블럭
         *
         * 첫 화면에선 1p~10p까지 볼 수 있음
         * @type {number}
         */
        let pageBlock = 10;
        /**
         * 현재 페이지가 20p라면 (pageNo = 19)
         *
         * blockNo는 Math.floor(19/10) + 1 = 2
         * @type {number}
         */
        let blockNo = Math.floor(pageNo / pageBlock) + 1;
        /**
         * 현재 페이지가 20p라면 blockNo는 2
         *
         * (2 - 1) * pageBlock = 10
         *
         * @type {number}
         */
        let startPageNo = (blockNo - 1) * pageBlock;
        /**
         * 마찬가지로 20p일 때 blockNo는 2
         *
         * 2 * 10 - 1 = 19
         *
         * for문에서 1을 더해주기 때문에 20까지 보여준다.
         * @type {number}
         */
        let endPageNo = blockNo * pageBlock - 1;
        /**
         * 끝 페이지 번호가 전체 페이지 수보다 작을 때는
         *
         * 끝 번호를 전체 페이지로 지정해준다.
         */
        if (endPageNo > totalPageCount - 1) {
            endPageNo = totalPageCount - 1;
        }

        /**
         * 이전 페이지 블럭 번호
         * @type {number}
         */
        let prevBlockPageNo = (blockNo - 1) * pageBlock - 1;
        /**
         * 다음 페이지 블럭 번호
         * @type {number}
         */
        let nextBlockPageNo = blockNo * pageBlock;

        let strHTML = "";

        // <, << 활성화/비활성화 처리
        if (prevBlockPageNo >= 0) {
            // <, << 활성화
            strHTML += "<li><a href='javascript:" + fn + "(" + 0 + ");' ><i style='color:black;' class=\"fa-solid fa-angles-left\"></i></a></li>";
            strHTML += "<li><a href='javascript:" + fn + "(" + prevBlockPageNo + ");' ><i style='color:black;' class=\"fa-solid fa-angle-left\"></i></a></li>";
        } else {
            // <, << 비활성화
            strHTML += "<li><a><i style='color:grey;' class=\"fa-solid fa-angles-left\"></i></a></li>";
            strHTML += "<li><a><i style='color:grey;' class=\"fa-solid fa-angle-left\"></i></a></li>";
        }
        for (let i = startPageNo; i <= endPageNo; i++) {
            if (i == pageNo) {
                strHTML += "<li class='pageActive'><a>" + (i + 1) + "</a></li>";
            } else {
                strHTML += "<li><a href='javascript:" + fn + "(" + i + ");' >" + (i + 1) + "</a></li>";
            }
        }

        // >, >> 활성화/비활성화 처리
        if (nextBlockPageNo < totalPageCount) {
            // >, >> 활성화
            strHTML += "<li><a href='javascript:" + fn + "(" + nextBlockPageNo + ");' ><i style='color:black;' class=\"fa-solid fa-angle-right\"></i></a></li>";
            strHTML += "<li><a href='javascript:" + fn + "(" + (totalPageCount - 1) + ");' ><i style='color:black;' class=\"fa-solid fa-angles-right\"></i></a></li>";
        } else {
            // >, >> 비활성화
            strHTML += "<li><a><i style='color:grey;' class=\"fa-solid fa-angle-right\"></i></a></li>";
            strHTML += "<li><a><i style='color:grey;' class=\"fa-solid fa-angles-right\"></i></a></li>";
        }

        let element = document.querySelector("#pagingArea");
        element.innerHTML = strHTML;
    },
    pageRowNumber(pageNo, pageSize, index, totalCount) {
        if (totalCount) {
            return totalCount - ((pageNo) * pageSize + index);
        } else {
            return (pageNo) * pageSize + (index + 1);
        }
    }
}