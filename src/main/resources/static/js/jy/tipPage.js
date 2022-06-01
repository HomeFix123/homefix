$(function() {
// 전체견적 리스트 페이징 ===========================================
	// 전체 페이지 수
	const page = pageCnt;
	
	console.log(page);
	// 현재 페이지
	// url 주소에서 page 파라미터 값 불러옴
	const params = new URLSearchParams(location.search);
	
	let currentPage;
	if(!params.has('page')){
		currentPage = 1;
	} else {
		currentPage = parseInt(params.get("page"));
	}
	
	// 페이지 분류
	// 현재 페이지의 분류 번호
	// ex 1~5 => 0
	// ex 6~10 => 1 ...
	const classify = parseInt((currentPage -1)/5);
	
	// 페이징 버튼 ul
	const ul = $('#pagination');
	ul.empty(); // 버튼들 초기화
	
	// 이전 버튼
	// 1~5 페이지에 있다면 이전 버튼을 출력하지않음
	if(classify > 0){
		let li = "<li class='float-left mb-0'>"
		li += "<a class='page-numbers' href='list?page="+ ((classify-1)*5+1) +"'>"
		li += "<i class='fas fa-chevron-left'></i>"
		li += "</a>"
		li += "</li>"
		ul.append(li);
	}
	
	// 숫자 버튼들
	for(let i = 1; i <= 5; i++){
		// 전체 페이지보다 더 큰 페이지 번호를 보여주지 않음
		if(classify*5+i > page) break;
		
		let li = "<li class='mb-0'>"
		if(classify*5+i == currentPage){
			// 현재 페이지인 경우
			li += "<a class='page-numbers current'>"
		} else {
			// 현재 페이지가 아닌 경우
			li += "<a class='page-numbers' href='list?page="+ (classify*5+i) +"'>"
		}
		li += classify*5+i
		li += "</a>"
		li += "</li>";
		
		ul.append(li);
	}
	
	// 다음 버튼
	if(classify < parseInt((page-1)/5)){
		let li = "<li class='float-right mb-0'>"
		li += "<a class='page-numbers' href='list?page="+ (classify*5+6) +"'>"
		li += "<i class='fas fa-chevron-right'></i>"
		li += "</a>"
		li += "</li>"
		ul.append(li);
	}
	
	});