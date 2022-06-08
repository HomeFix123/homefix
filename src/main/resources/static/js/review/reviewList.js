// 검색 기능
/*
	요약: 검색창과 버튼을 form 태그로 감싸고 데이터를 보냄
	
	1. 지역 모달에서 선택시 input[hidden]으로 form 안에 지역 데이터 추가
	2. 리뷰순, 계약순, 정확도순 클릭시 input[hidden]으로 form 안에 sort 추가 후 form => submit 
	
*/

$(function(){
	// 검색 폼
	const searchForm = $('#searchForm');
	
	const hometypeModal = $('#hometypeModal');
	const jobModal = $('#jobModal')
	const familyModal = $('#familyModal');
	
	// 모달에서 지역 선택시 input[hidden]으로 form 안에 데이터 추가
	// input[hidden] 요소 담는 곳
	const hiddenInputs = $('#hiddenInputs');
	
	
	// 모달 관련
	const ulHometypeBtns = $('#ulHometypeBtns');
	const ulJobBtns = $('#ulJobBtns');
	const ulFamilyBtns = $('#ulFamilyBtns');
	
	const ulBtns = $('.ulBtns')
	
	// 검색 필터 제거 버튼
	const hometypeRemoveBtn = $('#hometypeRemoveBtn')
	const jobRemoveBtn = $('#jobRemoveBtn')
	const familyRemoveBtn = $('#familyRemoveBtn')
	
	
	// 버튼 선택시 이펙트
	ulBtns.find('button').click(function(){
		if($(this).hasClass('btn-selected')){
			$(this).removeClass('btn-selected')
		} else {
			ulBtns.find('button').removeClass('btn-selected')
			$(this).addClass('btn-selected')
		}
	})
	
	// 취소 버튼 선택시 이펙트 삭제
	$('.modalClose').click(() => {
		ulBtns.find('button').removeClass('btn-selected')
	})
	
	// 선택 버튼 선택시 input[hidden] 추가 
	hometypeModal.find('.modalSubmit').click(function(){
		const value = ulHometypeBtns.find('.btn-selected').attr('value')
		hiddenInputs.find('input[name=hometype]').remove();
		if(value != null){
			const input = "<input type='hidden' name='hometype' value='" + value + "'>"
			hiddenInputs.append(input)
		}
		
		searchForm.submit()
	})
	jobModal.find('.modalSubmit').click(function(){
		const value = ulJobBtns.find('.btn-selected').attr('value')
		hiddenInputs.find('input[name=job]').remove();
		if(value != null){
			const input = "<input type='hidden' name='job' value='" + value + "'>"
			hiddenInputs.append(input)
		}
		searchForm.submit()
	})
	familyModal.find('.modalSubmit').click(function(){
		const value = ulFamilyBtns.find('.btn-selected').attr('value')
		hiddenInputs.find('input[name=family]').remove();
		if(value != null){
			const input = "<input type='hidden' name='family' value='" + value + "'>"
			hiddenInputs.append(input)
		}
		searchForm.submit()
	})
	
	hometypeRemoveBtn.click(function(){
		hiddenInputs.find('input[name=hometype]').remove();
		searchForm.submit()
	})
	jobRemoveBtn.click(function(){
		hiddenInputs.find('input[name=job]').remove();
		searchForm.submit()
	})
	familyRemoveBtn.click(function(){
		hiddenInputs.find('input[name=family]').remove();
		searchForm.submit()
	})
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
	const listDiv = $('#reviewListDiv'); // 검색결과를 추가할 곳
	const divSample = $('.reviewDiv:first-child()').clone(); // div 형태 복사
	
	const params = new URLSearchParams(location.search);
	
	// url에서 가져온 검색 조건들을 data 객체에 추가
	if(params.has('hometype')){
		data.keyword = params.get("hometype");
	}
	if(params.has('job')){
		data.sort = params.get("job");
	}
	if(params.has('family')){
		data.area = params.get("family");
	}
	
	// 더보기 버튼 클릭
	$('#moreBtn').click(() => {
		page += 1; // 다음 페이지의 데이터 요청
		
		data.page = page // 데이터에 페이지 데이터 추가
		
		const resultList = getMoreData(data) // ajax로 요청후 결과를 변수에 저장
		
		
		if(resultList < 12){
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
			
			const cnt = div.find('.cnt'); // 조회수
			const link = div.find('.relink');
			
			const hometypeSpan = div.find('.htspan');
			const sizeSpan = div.find('.sizespan');
			
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
			cnt.text(result.cnt);
			contract.text(result.contract);
			link.attr('href', '/review/' + result.id)
			// 전문분야 
			hometypeSpan.text(result.hometype)
			sizeSpan.text(result.size)
			
			// 만들어진 div를 검색결과리스트에 추가
			listDiv.append(div)
		}
	})
	
	
	// 추가 데이터 가져오는 ajax
	function getMoreData(data){
		
		let result = null;
		
		$.ajax({
			type: "GET",
			url: "/review/page",
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