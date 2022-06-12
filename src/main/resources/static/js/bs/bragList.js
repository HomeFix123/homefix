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
	const ulBtns = $('#ulBtns');
	const ulBtnsHometype = $('#ulBtnshometype');
	const ulBtnsFamily = $('#ulBtnsfamily');

	const locSubmitBtn = $('#modalSubmit');
	const locCancelBtn = $('#modalCancelBtn');
	const hometypeSubmitBtn = $('#hometypeSubmit');
	const hometypeCancelBtn = $('#hometypeCancelBtn');
	const familySubmitBtn = $('#familySubmit');
	const familyCancelBtn = $('#familyCancelBtn');
	
	
	// 지역 버튼 선택시 이펙트
	ulBtns.find('button').click(function(){
		if($(this).hasClass('btn-selected')){
			$(this).removeClass('btn-selected')
		} else {
			ulBtns.find('button').removeClass('btn-selected')
			$(this).addClass('btn-selected')
		}
	})
	
		// 주거형태 버튼 선택시 이펙트
	ulBtnsHometype.find('button').click(function(){
		if($(this).hasClass('btn-selected')){
			$(this).removeClass('btn-selected')
		} else {
			ulBtnsHometype.find('button').removeClass('btn-selected')
			$(this).addClass('btn-selected')
		}
	})
	
		// 가족형태 버튼 선택시 이펙트
	ulBtnsFamily.find('button').click(function(){
		if($(this).hasClass('btn-selected')){
			$(this).removeClass('btn-selected')
		} else {
			ulBtnsFamily.find('button').removeClass('btn-selected')
			$(this).addClass('btn-selected')
		}
	})
	
	// 취소 버튼 선택시 이펙트 삭제
	locCancelBtn.click(() => {
		ulBtns.find('button').removeClass('btn-selected')
		
	})
	hometypeCancelBtn.click(() => {
		ulBtnsHometype.find('button').removeClass('btn-selected')
		
	})
	familyCancelBtn.click(() => {
		ulBtnsFamily.find('button').removeClass('btn-selected')
		
	})
	
	
	locSubmitBtn.click(() => {modalSubmitBtn()});
	hometypeSubmitBtn.click(() => {modalSubmitBtn()});
	familySubmitBtn.click(() => {modalSubmitBtn()});
	
	
	// 선택 버튼 선택시 input[hidden] 추가 
	function modalSubmitBtn(){
		const locName = ulBtns.find('.btn-selected').attr('value')
		const hometypeName = ulBtnsHometype.find('.btn-selected').attr('value')
		const familyName = ulBtnsFamily.find('.btn-selected').attr('value')
		
		// 남아있던 검색값 삭제 후 고른 걸로 추가
		hiddenInputs.find('input[name=loc]').remove();
		hiddenInputs.find('input[name=hometype]').remove();
		hiddenInputs.find('input[name=family]').remove();
		if(locName != null){
			const input = "<input type='hidden' name='loc' value='" + locName + "'>"
			hiddenInputs.append(input)
		} else if(hometypeName != null){
			const input = "<input type='hidden' name='hometype' value='" + hometypeName + "'>"
			hiddenInputs.append(input)
		} else if(familyName != null){
			const input = "<input type='hidden' name='family' value='" + familyName + "'>"
			hiddenInputs.append(input)
		}
		
		
		searchForm.submit();
		
	};
	
	// 검색 폼
	const searchForm = $('#searchForm');
	
	// 정렬 버튼들 
	const sortBtn = $('#sortBtn'); // 정확도순
	const sortPreferBtn = $('#sortPreferBtn'); // 리뷰순
	const sortCntBtn = $('#sortCntBtn'); // 계약순
	
	
	sortBtn.click(() => {clickSortBtn()});
	sortPreferBtn.click(() => {clickSortBtn('prefer')});
	sortCntBtn.click(() => {clickSortBtn('cnt')});
	
	
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
	const listDiv = $('#bragListDiv'); // 검색결과를 추가할 곳
	const divSample = $('.bragDiv:first-child()').clone(); // div 형태 복사
	
	const params = new URLSearchParams(location.search);
	
	
	// url에서 가져온 검색 조건들을 data 객체에 추가
	if(params.has('family')){
		data.family = params.get("family");
	}
	if(params.has('sort')){
		data.sort = params.get("sort");
	}
	if(params.has('loc')){
		data.loc = params.get("loc");
	}
	if(params.has('hometype')){
		data.hometype = params.get("hometype");
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
				sort: "정렬", 
				loc: "지역",
				hometype:"주거형태",
				family:"가족형태",
				page: 2
			}
		*/
		
		const resultList = getMoreBrag(data) // ajax로 요청후 결과를 변수에 저장
		
		if(resultList.length < 12){
			$('#moreDiv').remove();
		}
		
		const imgURL = "http://140.238.11.118:5000/upload/" // 이미지 주소
		
		// 결과 개수만큼 for문
		for(let result of resultList){
			// divSample(검색결과를 담을 모양)을 다시 복사
			const div = divSample.clone();
			console.log(result)
			// 데이터 넣을 위치
			const title = div.find('.title');
			const mainImg = div.find('#thumbnailImage'); // 대표 이미지
			const profileimg = div.find('.user-img'); // 프로필 이미지
			const nickname = div.find('.nickname'); // 유저닉네임
			const prefer = div.find('.preferCnt'); // 좋아요수
			const cnt = div.find('.cnt'); // 조회수
			const preferIcon = div.find('#preferIcon'); // 북마크아이콘 처리
			const linkImg = div.find('#linkImg');
			const linkTitle = div.find('#linkTitle');
			
			// 이미지 처리
			mainImg.attr('src', imgURL + result.mainimg);
			profileimg.attr('src', imgURL + result.profileimg);
			
			// 단순한 데이터 처리 (업체명, 좋아요수, 계약건수)
			title.text(result.title);
			nickname.text(result.member.nickname);
			prefer.text(result.prefer);
			cnt.text(result.cnt);
			linkImg.attr('href', '/brag/'+ result.id);
			linkTitle.attr('href', '/brag/'+ result.id);
			
			// 좋아요 아이콘
			// 좋아요 여부에 따라 다른 아이콘 추가
			preferIcon.empty(); // 기존 아이콘 제거
			
			if(result.preferck){ // result.preferck(true: 좋아요 해둠 / false: 좋아요 안함)
				let iconTag = "<i class='fas fa-heart'></i>"
				preferIcon.append(iconTag)	
			}else {
				let iconTag = "<i class='far fa-heart'></i>"
				preferIcon.append(iconTag)
			}
			
			// 만들어진 div를 검색결과리스트에 추가
			listDiv.append(div)
		}
	})
	
	
	// 추가 데이터 가져오는 ajax
	function getMoreBrag(data){
		let result = null;
		
		$.ajax({
			type: "GET",
			url: "/brag/page",
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