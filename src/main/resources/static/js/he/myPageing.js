//고객이 쓴 후기 페이징--------------------------------------------------------
$(function() {
	let page = 0;
	let totalPages;
	// 버튼 클리이벤트
	$("#myBrag").click(function(){
		//alert("확인")
		page = page+1;
			$.ajax({
				url : "/sign/member/myPageing",
				type : "GET",
				data : {
					page : page,
				},
				success : function(getBragList) {
					// for문 시작
					totalPages = getBragList.totalPages
					//alert(totalPages)
					$.each(getBragList.content, (i, review) => {
						let row = ' <tr>'
							+ ' <td>'
							+ ' <a href="/brag/BragDetail(id=${'+review.bid+'})" >'+review.btitle+'</a>'
							+ ' </td>'
							+' <td>'+review.bcnt
							+' </td>'
							+' <td>'+review.bdate
							+' </td>'
							+' </tr>'
						$('#bragRe').append(row);
						// for문 끝
					})// end of for
					 if(page == (totalPages-1)){
		               $('#myBrag').css("visibility", "hidden");
		            	}//현재페이지와 totalPage가 같으면 스타일을 visibility: hidden 로 바꿔주기
				},
			})
	})// end of $("#myBrag").click()
	
})	//$(function() end


//고객이 쓴 팁 글 페이징--------------------------------------------------------
$(function() {
	let tippage = 0;
	let totalTipPages;
	// 버튼 클리이벤트
	$("#myTip").click(function(){
		//alert("확인")
		tippage = tippage+1;
			$.ajax({
				url : "/sign/member/myTipPageing",
				type : "GET",
				data : {
					page : tippage,
				},
				success : function(getTipList) {
					// for문 시작
					totalTipPages = getTipList.totalPages
					//alert(totalTipPages)
					$.each(getTipList.content, (i, tips) => {
						let rowTip = ' <tr>'
							+ ' <td>'
							+ ' <a href="/tip/list(id=${'+tips.tid+'})" >'+tips.tiptitle+'</a>'
							+ ' </td>'
							+ ' <td>'+tips.member.id
							+ ' </td>'
							+ ' </tr>'
						$('#tipBd').append(rowTip);
						// for문 끝
					})// end of for
					if(tippage == (totalTipPages-1)){
		               $('#myTip').css("visibility", "hidden");
		            	}//현재페이지와 totalPage가 같으면 스타일을 visibility: hidden 로 바꿔주기
				},
			})
	})// end of $("#myBrag").click()
	
})	//$(function() end


//고객이 좋아요 찍은 글 페이징---------- 왜 안돼냐고~~~!!!! ----------------------------------------------
$(function() {
	let lovepage = 0;
	let totalLovePages;
	// 버튼 클리이벤트
	$("#myLove").click(function(){
		alert("확인")
		lovepage = lovepage+1;
			$.ajax({
				url : "/sign/member/myLovePageing",
				type : "GET",
				data : {
					page : lovepage,
				},
				success : function(getLoveList) {
					// for문 시작
					totalLovePages = getLoveList.totalPages
					alert(totalLovePages)
					$.each(totalLovePages.content, (i, love) => {
						let rowLove = ' <div class="col-lg-4">'
							+ ' <div class="sa-post">'
							+ ' <div class="entry-header" >'
							+ ' <div class="entry-thumbnail">'
							+ ' <a href="/brag/(${'+love.brag.bid+'})" >'
							+ ' <img src="|http://140.238.11.118:5000/upload/${'+love.brag.bimgadr+'}|">'
							+ ' </a>'
							+ ' </div>'
							+ ' <div class="entry-content">'
							+ ' <h2 class="entry-title">'+love.brag.btitle
							+ ' </h2>'
							+ ' </div>'
							+ ' </div>'
							+ ' </div>'
						$('#fav_row').append(rowLove);
						// for문 끝
					})// end of for
					if(lovepage == (totalLovePages-1)){
		               $('#myLove').css("visibility", "hidden");
		            	}//현재페이지와 totalPage가 같으면 스타일을 visibility: hidden 로 바꿔주기
				},
			})
	})// end of $("#myBrag").click()
	
})	//$(function() end


//고객이 좋아요 찍은 업체목록 페이징--------------------------------------------------------
$(function() {
	let LoveCompage = 0;
	let totalLoveComPages;
	// 버튼 클리이벤트
	$("#myCompany").click(function(){
		alert("확인")
		LoveCompage = LoveCompage+1;
			$.ajax({
				url : "/sign/member/myTipPageing",
				type : "GET",
				data : {
					page : LoveCompage,
				},
				success : function(getLoveComList) {
					// for문 시작
					totalLoveComPages = getLoveComList.totalPages
					alert(totalLoveComPages)
					$.each(getLoveComList.content, (i, loveCom) => {
						let rowLoveCom = ' <div class="col-lg-4">'
							+ ' <div class="sa-post">'
							+ ' <div class="entry-header" >'
							+ ' <div class="entry-thumbnail">'
							+ ' <a href="/brag/(${'+loveCom.company.id+'})" >'
							+ ' <img src="|http://140.238.11.118:5000/upload/${'+loveCom.company.logo+'}|">'
							+ ' </a>'
							+ ' </div>'
							+ ' <div class="entry-content">'
							+ ' <h2 class="entry-title">'+loveCom.company.name
							+ ' <a href="/company/${'+loveCom.company.id+'}">'
							+ ' </a>'
							+ ' </h2>'
							+ ' </div>'
							+ ' </div>'
							+ ' </div>'
						$('fav_row com').append(rowLoveCom);
						// for문 끝
					})// end of for
					if(LoveCompage == (totalLoveComPages-1)){
		               $('#myCompany').css("visibility", "hidden");
		            	}//현재페이지와 totalPage가 같으면 스타일을 visibility: hidden 로 바꿔주기
				},
			})
	})// end of $("#myBrag").click()
	
})	//$(function() end