$(function() {
	
	//CIng.html 견적 클릭시 견적 상세 나오고 스크롤 그 견적상세로 보내기
	$(".progress_space").on("click", function() {
		//				$("#progress_detail").css("display", "block");
		let offset = $(".progress_detail").offset();
		$('html,body').animate({ scrollTop: offset.top }, 400);
	});

	$(".complete_btn.btn.btn-primary").unbind('mouseenter mouseleave');

	//채팅하기 버튼 클릭시 채팅띄우기(MPickCDetail)
	$(".chat_btn").on("click",function(){
		
		window.open("http://localhost:3000/chat?roomCode='+ 1'", 'chatting', 'width=500px, height=800px');
	});





});

