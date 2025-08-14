let codeList = [];
let toast;
$(document).ready(async function () {
    toast = window.__toast;
    initDatePicker();
    setCntPerPageInit();
    inputNumber();
})

function pageReload() {
    const newParams = {...getQueryParamAsJson()};
    const newQueryParams = jsonToQueryParam(newParams);
    location.href = location.pathname + '?' + newQueryParams;
}

async function getCodeList() {
    const codeRes = await axios.post('/common/codeList', {});
    if (!codeRes || !codeRes.data) {
        alert("codeList를 불러오는데 실패했습니다.");
        return;
    }

    codeList = codeRes.data.list;

}

function initTextEditor() {
    // 텍스트에디터 기초 세팅
    tinymce.init({
        language: "ko_KR",
        selector: 'textarea',
        height: 500,
        plugins: [
            // Core editing features
            'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount',
        ],
        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
        tinycomments_mode: 'embedded',
        tinycomments_author: 'Author name',
        mergetags_list: [
            {value: 'First.Name', title: 'First Name'},
            {value: 'Email', title: 'Email'},
        ],
        ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
    });
}

function initDatePicker() {
    $(".datepickerInput").each(function () {
        $(this).datepicker({
            showAnim: "slideDown",
            showOtherMonths: false,
            changeYear: true,
            changeMonth: true,
            dateFormat: "yy.mm.dd",
            showMonthAfterYear: true,
            yearSuffix: ".",
            dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            monthNamesShort: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            onSelect: function (dateText) {
                // Set the selected date to .input field
// 			$(this).siblings('.input').val(dateText);
            }
        }).datepicker(); // Set the default date
    });
}

// 페이지 개수 선택
function setCntPerPageInit() {
    const jsonUrlParam = getQueryParamAsJson();

    if (jsonUrlParam['cntPerPage']) {
        $('#pageSize').val(decodeURI(jsonUrlParam['cntPerPage'])).prop("selected", true);
    } else {
        $("#pageSize option:eq(0)").prop("selected", true);
    }

}

function setSearchInit(searchClass) {
    // searchOption 선택 기능
    const jsonUrlParam = getQueryParamAsJson();
    for (key in jsonUrlParam) {
        if (!key || !jsonUrlParam[key] || !$('.' + searchClass).find('#' + key)) {
            continue;
        }

        if (!$('.' + searchClass).find('#' + key).prop('tagName')) {
            continue;
        }
        const tag = $('.' + searchClass).find('#' + key).prop('tagName').toLowerCase();

        // 날짜패턴 정규식 YYYY-MM-DD
        var regex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/);
        if (regex.test(jsonUrlParam[key])) {
            jsonUrlParam[key] = dayjs(jsonUrlParam[key]).format('YYYY.MM.DD');
        }

        if (tag === 'input') {
            $('.' + searchClass).find('#' + key).val(decodeURI(jsonUrlParam[key]));
        } else if (tag === 'select' || tag === 'radio') {
            $('.' + searchClass).find('#' + key).val(decodeURI(jsonUrlParam[key])).prop("selected", true);
        }
    }

    $('.' + searchClass).find('button.searchBtn').click(function () {
        //const nowParams = getQueryParamAsJson();
        const newParams = searchObject(searchClass);
        for (key in newParams) {
            if (!newParams[key]) {
                continue;
            }
            // 날짜패턴 정규식 YYYY.MM.DD
            var regex = RegExp(/^\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01])$/);
            if (regex.test(newParams[key])) {
                newParams[key] = dayjs(newParams[key]).format('YYYY-MM-DD');
            }
            //nowParams[key] = newParams[key];
        }

        // 객체 nowParams를 쓰면 다중조건 검색후에 일부 조건을 빼서 재검색하려면 param[key]값이 유지가 되어서 삭제(주석처리해둠)
        //const newQueryParams = jsonToQueryParam(nowParams);
        const newQueryParams = jsonToQueryParam(newParams);
        const url = newQueryParams ? location.pathname + '?' + newQueryParams : location.pathname;
        location.href = url;
    })
}

function searchObject(searchClass) {
    let param = {};

    $('.' + searchClass + ' :input').each(function () {
        let id = "";
        let value = "";

        if ($(this).attr("type") == "radio") {
            if ($(this).prop("checked")) {
                id = $(this).attr("name");
                value = $(this).val();
            }
        } else {
            id = $(this).attr("id");
            value = $(this).val();
        }

        if (value.trim() != '') {
            param[id] = value;
        }
        ;

    });
    return param;
}

function getQueryParamAsJson() {
    const urlParams = new URLSearchParams(window.location.search);
    const jsonObject = Object.fromEntries(urlParams.entries());
    return jsonObject;
}

function jsonToQueryParam(json) {
    const urlParams = new URLSearchParams();

    for (const [key, value] of Object.entries(json)) {
        urlParams.append(key, value);
    }

    return urlParams.toString();
}

/*
	selectbox, option 생성
*/
function createSelectOptions(selectId, collection, options) {
    if (options.isSetAll) {
        $(`#${selectId}`).append(`<option value="">전체</option>`);
    }

    if (options.isSetBlank) {
        $(`#${selectId}`).append(`<option value="">선택</option>`);
    }

    if (collection instanceof Map) {
        collection.forEach((value, key) => {
            $(`#${selectId}`).append(
                options && (options.selectedValue === key) ?
                    `<option value="${key}" selected>${value}</option>`
                    : `<option value="${key}">${value}</option>`
            );
        });
    } else {
        if (!options || !options.keyNm || !options.valueNm) {
            throw 'Option is not valid.' + JSON.stringify(options);
        }
        collection.forEach(item => {
            $(`#${selectId}`).append(
                options.colorCd && options.selectedValue === item[options.keyNm]
                    ? `<option value="${item[options.keyNm]}" data-colorCd=${item[options.colorCd]} selected>${item[options.valueNm]}</option>`
                    : options.colorCd
                        ? `<option value="${item[options.keyNm]}" data-colorCd=${item[options.colorCd]}>${item[options.valueNm]}</option>`
                        : options.selectedValue === item[options.keyNm]
                            ? `<option value="${item[options.keyNm]}" selected>${item[options.valueNm]}</option>`
                            : `<option value="${item[options.keyNm]}">${item[options.valueNm]}</option>`
            );
        });
    }
}

/*
	radio button 생성
 */
function createRadioButtons(selectId, collection, options) {
    if (!options || !options.keyNm || !options.valueNm) {
        throw 'Option is not valid.' + JSON.stringify(options);
    }
    collection.forEach((item, idx) => {
        $(`#${selectId}`).append(
            options.selectedValue === item[options.keyNm] ?
                `<div class="rad_box"><input type="radio" name="${selectId}" value="${item[options.keyNm]}" id="${selectId}${idx}" checked>
					<label for="${selectId}${idx}">${item[options.valueNm]}</label></div>`
                :
                `<div class="rad_box"><input type="radio" name="${selectId}" value="${item[options.keyNm]}" id="${selectId}${idx}">
					<label for="${selectId}${idx}">${item[options.valueNm]}</label></div>`
        );
    });

}

function phoneValidate(event) {
    const value = event.currentTarget.value

    if (value.length > 0 && value[0] != '0') {
        event.currentTarget.value = '';
        alert("휴대폰 번호 형식에 맞지 않습니다.")
        return;
    }
    if (value.length > 1 && value[1] != '1') {
        event.currentTarget.value = '0';
        alert("휴대폰 번호 형식에 맞지 않습니다.")
        return;
    }
    if (value.length > 13) {
        event.currentTarget.value = value.substring(0, 13)
        return;
    }
    event.currentTarget.value = value.replace(/[^0-9]/g, '')
        .replace(/(^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3")
}

// 공통 팝업
function openCommonPopup(popupNm, title, width, height) {
    let _width = width ? width : '1000';
    let _height = height ? height : '640';

    // 팝업을 가운데 위치
    let _left = Math.ceil((window.screen.width - _width) / 2);
    let _top = Math.ceil((window.screen.height - _height) / 2);

    let url = `/common/popup/${popupNm}?title=` + encodeURIComponent(title);
    console.log(encodeURIComponent(title));
    let popupName = title + '_' + new Date().getTime(); // 이름 중복 방지

    let popup = window.open(url, popupName, `width=${_width},height=${_height},left=${_left},top=${_top},resizable=no,scrollbars=yes,status=no`);
    popup.document.title = title;
}

/* 뒤에 '원' 붙힐 지 말지, 둘중 하나 사용 */
function formatNumberWithComma(number) {
	// 빈 문자열 또는 NaN 방지
	if (isNaN(number)) return '0';
	return Number(number).toLocaleString('ko-KR');
}

function formatToWon(num) {
	if (!num) return "";
	return Number(num).toLocaleString('ko-KR') + "원";
}

/* 둘중 하나 사용 */

//input number 입력 이벤트
function inputNumber() {
    $("input[data-type=number]").on("keyup", function (e) {
        // 원래 입력값
        const rawValue = e.target.value;
        // 콤마제외 유효성 검사
        let cleaned = String(rawValue).replace(/[^\d]/g, '');
        // 콤마가 붙은 새 값
        const formatted = formatNumberWithComma(cleaned);
        // 입력값 업데이트
        e.target.value = formatted;
    });
}

// 콤마 제거
function parseNumber(value) {
    if (typeof value === "string") {
        return Number(value.replace(/,/g, '')) || 0;
    }
    return Number(value) || 0;
}

