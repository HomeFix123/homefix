// 검색 기능
/*
	요약: 검색창과 버튼을 form 태그로 감싸고 데이터를 보냄
	
	1. 지역 모달에서 선택시 input[hidden]으로 form 안에 지역 데이터 추가
	2. 리뷰순, 계약순, 정확도순 클릭시 input[hidden]으로 form 안에 sort 추가 후 form => submit 
	
*/

$(function(){
	// 모달에서 지역 선택시 input[hidden]으로 form 안에 데이터 추가
	// input[hidden] 요소 담는 곳
	const hiddenInputs = $('#hiddenInputs');
	
	
	// 모달 관련
	const modal = $('#exampleModal');
	const ulBtns = $('#ulBtns');
	const modalSubmitBtn = $('#modalSubmit');
	const modalCancelBtn = $('#modalCancelBtn');
	
	
	
	
	// 지역 버튼 선택시 이펙트
	ulBtns.find('button').click(function(){
		if($(this).hasClass('btn-selected')){
			$(this).removeClass('btn-selected')
		} else {
			ulBtns.find('button').removeClass('btn-selected')
			$(this).addClass('btn-selected')
		}
	})
	
	// 취소 버튼 선택시 이펙트 삭제
	modalCancelBtn.click(() => {
		ulBtns.find('button').removeClass('btn-selected')
	})
	
	// 선택 버튼 선택시 input[hidden] 추가 
	modalSubmitBtn.click(function(){
		const areaName = ulBtns.find('.btn-selected').attr('value')
		
		// 남아있던 지역 검색값 삭제 후 고른 걸로 추가
		hiddenInputs.find('input[name=area]').remove();
		if(areaName != null){
			const input = "<input type='hidden' name='area' value='" + areaName + "'>"
			hiddenInputs.append(input)
		}
		
		modal.modal("hide"); // 모달 닫기 (자동으로 안닫혀서 추가)
	});
	
	// 검색 폼
	const searchForm = $('#searchForm');
	
	// 정렬 버튼들 
	const sortBtn = $('#sortBtn'); // 정확도순
	const sortReviewBtn = $('#sortReviewBtn'); // 리뷰순
	const sortContractBtn = $('#sortContractBtn'); // 계약순
	
	
	sortBtn.click(() => {clickSortBtn()});
	sortReviewBtn.click(() => {clickSortBtn('review')});
	sortContractBtn.click(() => {clickSortBtn('contract')});
	
	
	function clickSortBtn(sort){
		// sort가 없으면 input 추가되지 않고 기본 검색(정확도순)
		if(sort){
			// sort가 있으면 input 추가
			const input = "<input type='hidden' name='sort' value='" + sort + "'>"
			hiddenInputs.append(input)
		}
		searchForm.submit();
	}
})


// ajax로 검색 결과 추가
/*
	요약: 기존 검색 조건에 맞춰 ajax에 요청해서 데이터 추가
	
	1. url에서 기존 검색 조건(param)을 가져와서 data에 담음
	2. 더보기 버튼 클릭시 다음 페이지의 데이터를 서버에 요청
	3. 기존 html 페이지에 있는 div를 복사(clone)해서 가져온 데이터로 변경
	4. 만들어진 div를 하단에 추가 
*/
$(function(){
	let page = 1; // 초기 페이지
	let data = {}; // 검색할 데이터 (객체)
	const listDiv = $('#companyListDiv'); // 검색결과를 추가할 곳
	const divSample = $('.companyDiv:first-child()').clone(); // div 형태 복사
	
	const params = new URLSearchParams(location.search);
	
	// url에서 가져온 검색 조건들을 data 객체에 추가
	if(params.has('keyword')){
		data.keyword = params.get("keyword");
	}
	if(params.has('sort')){
		data.sort = params.get("sort");
	}
	if(params.has('area')){
		data.area = params.get("area");
	}
	/*
		data = {
			keyword: "검색어", 
			sort: "정렬", 
			area: "지역"
		}
	*/
	
	// 더보기 버튼 클릭
	$('#moreBtn').click(() => {
		page += 1; // 다음 페이지의 데이터 요청
		
		data.page = page // 데이터에 페이지 데이터 추가
		/*
			data = {
				keyword: "검색어", 
				sort: "정렬", 
				area: "지역",
				page: 2
			}
		*/
		
		const resultList = getMoreCompany(data) // ajax로 요청후 결과를 변수에 저장
		
		
		if(resultList < 10){
			// 결과가 10개 미만인 경우 더보기 버튼 삭제
			$('#moreDiv').remove(); 
		}
		
		const imgURL = "http://140.238.11.118:5000/upload/" // 이미지 주소
		const defaultImg = "profile_basic.png" // 기본 이미지
		
		// 결과 개수만큼 for문
		for(let result of resultList){
			// divSample(검색결과를 담을 모양)을 다시 복사
			const div = divSample.clone();
			
			// 데이터 넣을 위치
			const titleImg = div.find('.titleImg'); // 타이틀 이미지
			const logo = div.find('.user-img'); // 로고 이미지
			const companyName = div.find('.companyName'); // 업체명
			const companySpecialList = div.find('.companySpecialList'); // 전문분야 (주거공간, 상업공간)
			const prefer = div.find('.preferCnt'); // 좋아요수
			const contract = div.find('.contractCnt'); // 계약건수
			const bookmarkIcon = div.find('.bookmarkIcon'); // 북마크아이콘 처리
			
			
			// 이미지 처리
			if(result.img != null){
				titleImg.attr('src', imgURL + result.img);
			} else {
				titleImg.attr('src', imgURL + defaultImg);
			}
			
			if(result.logo != null){
				logo.attr('src', imgURL + result.logo);
			} else {
				logo.attr('src', imgURL + defaultImg);
			}
			
			// 단순한 데이터 처리 (업체명, 좋아요수, 계약건수)
			companyName.text(result.name);
			prefer.text(result.prefer);
			contract.text(result.contract);
			
			// 전문분야 
			// 데이터가 0개 ~ 2개
			companySpecialList.empty(); // sample에 있는 전문분야 비우기
			
			for(let spe of result.special){
				// 데이터 수만큼 a태그 추가
				let tag = "<a class='companySpecial btn btn-secondary m-1'>"
				tag += spe
				tag += "</a>"
				companySpecialList.append(tag);	
			}
			
			// 북마크 아이콘
			// 좋아요 여부(북마크 여부)에 따라 다른 아이콘 추가
			bookmarkIcon.empty(); // 기존 아이콘 제거
			
			if(result.isPrefer){ // result.isPrefer(true: 북마크 해둠 / false: 북마크 안함)
				let iconTag = "<i class='fas fa-bookmark'></i>"
				bookmarkIcon.append(iconTag)	
			}else {
				let iconTag = "<i class='far fa-bookmark'></i>"
				bookmarkIcon.append(iconTag)
			}
			
			// 만들어진 div를 검색결과리스트에 추가
			listDiv.append(div)
		}
	})
	
	
	// 추가 데이터 가져오는 ajax
	function getMoreCompany(data){
		
		let result = null;
		
		$.ajax({
			type: "GET",
			url: "/expert/page",
			data: data,
			async: false // 성공할때까지 대기 (없으면 return 값이 undefined)
		}).done((data) => {
			// 성공시 데이터 저장
			result = data;
		}).fail((err) => {
			// 실패시 에러 출력
			console.log(err);
		});
		
		return result;
	}
})