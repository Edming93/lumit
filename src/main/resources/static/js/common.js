/*
	selectbox, option 생성
*/
function createSelectOptions(selectId, collection, options) {
	if(options.isSetAll) {
		$(`#${selectId}`).append(`<option value="">전체</option>`);
	}
	
    if(options.isSetBlank) {
        $(`#${selectId}`).append(`<option value="">선택</option>`);
    }

	if(collection instanceof Map) {
		collection.forEach((value, key) => {
			$(`#${selectId}`).append(
				options && (options.selectedValue === key) ?
					`<option value="${key}" selected>${value}</option>`
					: `<option value="${key}">${value}</option>`
			);
		});
	} else {
		if(!options || !options.keyNm || !options.valueNm) {
			throw 'Option is not valid.' + JSON.stringify(options);
		}
		collection.forEach(item => {		
			$(`#${selectId}`).append(
				options.selectedValue === item[options.keyNm] ?
					`<option value="${item[options.keyNm]}" selected>${item[options.valueNm]}</option>`
					:`<option value="${item[options.keyNm]}">${item[options.valueNm]}</option>`
			);
		});
	}
}

/*
	radio button 생성
 */
function createRadioButtons(selectId, collection, options) {
	if(!options || !options.keyNm || !options.valueNm) {
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