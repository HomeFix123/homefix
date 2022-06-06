$(function(){
	//---sa-dropdown(알림) 버튼 외 누를 시 드롭다운 해제
	$(document).mouseup(function (e){
	  	var LayerPopup = $(".sa-dropdown");
	  	if(LayerPopup.has(e.target).length === 0){
	    	$(".yetest").css({"visibility":"hidden"
							,"opacity": "0"
							,"-webkit-transform": "rotateX(-75deg)"
							,"-moz-transform":"rotateX(-75deg);"
							,"-ms-transform":"rotateX(-75deg)"
							,"-o-transform":"rotateX(-75deg)"
							,"transform":"rotateX(-75deg)"
			})//=css
	  	}
	});//===sa-dropdown(알림) 버튼 외 누를 시 드롭다운 해제
	
	//--알림버튼 누를 시 db 불러오기  
	$(".sa-dropdown").on("click",function(){
		console.log("하이");
		/*ajax*/
		$.ajax({
			type:'get',
			url:'/chat/getChatList',
			contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
			success:function(result){
				$("#global-list").empty();
				console.log('채팅컨트롤러가기성공');
					console.log(result);
					console.log(result.user);
					console.log(result.list.length)
					if(result.user == "company"){
						console.log("회사다");
						for(let i=0 ; i < result.list.length ; i++){
						console.log(i);
						let tag = '<li class="remove-item"><span class="remove-icon">'
									+ '<i class="bi bi-chat-square-text"></i>'
									+ '</span>'
									+ '<div class="sa-course">'
									+ '<div class="course-info">'
									+ '<div class="info">'
									+ '<h2 class="title">'
									+ '<a href="course-details.html">'+ result.list[i].member.name +'</a>'
									+ '</h2>'
									+ '</div>'
									+ '</div>'
									+ '</div>'
									+ '</li>';
						console.log(tag);
						$("#global-list").append(tag);}
					}	
			},
			error : function(err) {
				//실패했을 때
    			alert('실패:');
    			console.log(err);
    		}
		})//ajax
		
		/*css 변경*/
		$(".yetest").css({"visibility":"visible"
							,"opacity": "1"
							,"-webkit-transform": "rotateX(0deg)"
							,"-moz-transform":"rotateX(0deg);"
							,"-ms-transform":"rotateX(0deg)"
							,"-o-transform":"rotateX(0deg)"
							,"transform":"rotateX(0deg)"
		})//=css
	})//=hover
	
	
})//=$(function(){})