$(document).ready(function() {
	// setSearchInit('searchArea');
	load();

    $(".searchArea input[type='text']").on("keydown", function (event) {
        if (event.key === 'Enter') {
            getList(0);
        };
    });

    $(".searchBtn").on("click", function(event) {
    	getList(0);
    });

    // 검색 결과 페이지 수 변경시
    $('#pageSize').change(function() {
		const cntPerPage = $(this).val();
		const page = 1;
		const nowParams = {page, cntPerPage};

		getList(0, cntPerPage);
	});
    
    $('#listArea').on('click','#popupImg', function() {
		let imgUrl = $(this).attr('src');
		
		const tempImg = new Image();
	    tempImg.src = imgUrl;
		
	    tempImg.onload = function () {
	        const imgWidth = tempImg.width;
	        const imgHeight = tempImg.height;

	        
	        // 팝업 크기와 위치 동적 계산
	        const popupWidth = imgWidth + 20; // 여백 포함
	        const popupHeight = imgHeight + 20;
	        const screenWidth = window.screen.width;
	        const screenHeight = window.screen.height;
	        const left = (screenWidth - popupWidth) / 2;
	        const top = (screenHeight - popupHeight) / 2;

	        // 팝업 창 열기
	        const popup = window.open(
	            '',
	            '_blank',
	            `width=${popupWidth}px, height=${popupHeight}px, top=${top}, left=${left}`
	        );

	        popup.document.write(`
                <style>
                    body { margin: 0; display: flex; align-items: center; justify-content: center; height: 100%; }
                    img { display: block; max-width: 100%; max-height: 100%; }
                </style>
                <img src="${imgUrl}">
            `);
	    };

	    tempImg.onerror = function () {
	        alert('이미지를 불러올 수 없습니다.');
	    };
    });

});

async function load() {
	await getCodeList();
	await getList();
	createSelectOptions("status", codeList.filter((item) => item.grpCd === 'PD_STATUS'),{isSetAll:true, keyNm:"cd", valueNm:"cdNm"});
	createSelectOptions("dpStatus", codeList.filter((item) => item.grpCd === 'DP_STATUS'),{isSetAll:true, keyNm:"cd", valueNm:"cdNm"});
}

async function getList(pageNo,cntPerPage) {
	pageNo = pageNo ?? 0;
	cntPerPage = cntPerPage ?? 10;

	let productName = $("#productName").val();
	let productCd = $("#productCd").val();
	let dpStatus = $("#dpStatus").val();
	let status = $("#status").val();
	let strDt = $("#strDt").val();
	let endDt = $("#endDt").val();

	params = {
		productName : productName,
		productCd : productCd,
		dpStatus : dpStatus,
		status : status,
		strDt : strDt,
		endDt : endDt,
		cntPerPage : cntPerPage
	}

	// let url = "/rest/admin/product/list?page="+pageNo;
	const res = await axios.get("/rest/" + siteId + "/product/list?page="+pageNo, {params});
	if (!res || !res.data) {
		openAlert({contents: '상품 목록을 불러오는데 실패했습니다. <br/> 다시 시도해주세요.'});
		return;
	};

	data = res.data.list ?? [];

	// 검색결과 노출 수 변경마다 totalPageCount 값 변경
	const totalPageCount = Math.ceil(data.totalElements / cntPerPage);

	PAGE.paging(totalPageCount, data.number, data.totalElements, "getList");
	
	// 체크박스 초기화
	$("input[name=chk]").prop("checked", false);
	$("#chkSelectAll").prop("checked", false);

	setList(data);
}

function setList(data){
	$('#listArea').empty();

	productList = data.content;
	let listLength = productList.length;
	// let productCodeList = codeList.filter((item) => {return item.grpCd === 'PRODUCT_DV_CD'});

	let cnt = 0;
	if(listLength > 0) {
		productList.forEach((item) => {
			let lumitFiles = item.filePath ? item.filePath.substring('2') : '';
			$('#listArea').append(`
				<tr>
					<td class='alCenter'>
						<div class='chk_box hide_label'>
							<input type='checkbox' id='chk_${cnt++}' name='chk' 
							data-productcd='${item.productCd}' 
							data-productname='${item.productName}'
							data-price='${item.disPrice}'
							 value='${item.productId}'>
                        </div>
					</td>
					<td class='alCenter'>
					 	${item.filePath ? 
							`<img src='${lumitFiles}/${item.fileNewName}' style='width:50px; height:50px; cursor:pointer; vertical-align: middle;' id='popupImg'>`
							: ''
					 	}
					</td>
					<td class='alCenter'>
						${item.productCd}
					</td>
					<td class='alCenter'>
						${item.dpStatus}
					</td>
					<td class='alCenter'>
						${item.status ? codeList.find((cdItem) => cdItem.grpCd === 'PD_STATUS' && cdItem.cd == item.status).cdNm ?? '' : ''}
					</td>
					<td class='alLeft tdTitle'>
						${item.productName}
					</td>
					<td class="alCenter">
						${formatToWon(item.price)}
					</td>
					<td class="alCenter">
						${formatToWon(item.disPrice)}
					</td>
					<td class='alCenter'>
						${item.stocks}개
					</td>
					<td class='alCenter'>
						${item.sales}개
					</td>
				</tr>
			`);
		});
	}else {
		$('#listArea').append(`
			<tr>
				<td class='alCenter' colspan='10'>검색 결과가 없습니다.</td>
			</tr>
		`);
	}
	
	// 체크박스
	$("#chkSelectAll").click(function(){
		if($("#chkSelectAll").is(":checked")){
			$("input[name=chk]").prop("checked", true);
		}else{
			$("input[name=chk]").prop("checked", false);
		}
	});
	
	$("input[name=chk]").click(function(){
		var total = $("input[name=chk]").length;
		var checked = $("input[name=chk]:checked").length;
	
		if(total != checked){
			$("#chkSelectAll").prop("checked", false);
		}else{
			$("#chkSelectAll").prop("checked", true);
		}
	});
	
	$("#addBtn").on("click",function() {
		let checkedList = new Array();
		$("input[name=chk]").each(function() {
			if($(this).is(":checked") == true) {
				
				let obj = {
					productId: $(this).val(),
					productCd: $(this).data("productcd"),
					productName: $(this).data("productname"),
					price: $(this).data("price"),
				}
				checkedList.push(obj);
			};
		});
		
		if(window.opener && !window.opener.closed) {
			window.opener.receiveOptionList(checkedList);
		};
		
		window.close();
	});
}

function formatToWon(num) {
	if (!num) return "";
	return Number(num).toLocaleString('ko-KR') + "원";
}
