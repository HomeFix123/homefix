	
$(function(){
	const page = pageCnt;
	const params = new URLSearchParams(location.search);
	
	let currentPage;
	if(!params.has('page')){
		currentPage = 1;
	} else {
		currentPage = parseInt(params.get("page"));
	}
	
	const classify = parseInt((currentPage -1)/5);
	
	const ul = $('#pagination');
	ul.empty();
	if(classify > 0){
		let li = "<li class='float-left mb-0'>"
		li += "<a class='page-numbers' href='tip?page="+ ((classify-1)*5+1) +"'>"
		li += "<i class='fas fa-chevron-left'></i>"
		li += "</a>"
		li += "</li>"
		ul.append(li);
	}
	for(let i = 1; i <= 5; i++){
		if(classify*5+i > page) break;
		
		let li = "<li class='mb-0'>"
		if(classify*5+i == currentPage){
			li += "<a class='page-numbers current'>"
		} else {
			li += "<a class='page-numbers' href='tip?page="+ (classify*5+i) +"'>"
		}
		li += classify*5+i
		li += "</a>"
		li += "</li>";
		
		ul.append(li);
	}
	if(classify < parseInt((page-1)/5)){
		let li = "<li class='float-right mb-0'>"
		li += "<a class='page-numbers' href='tip?page="+ (classify*5+6) +"'>"
		li += "<i class='fas fa-chevron-right'></i>"
		li += "</a>"
		li += "</li>"
		ul.append(li);
	}
	
})