// 댓글 달기
$(function() { 

// 댓글 입력 버튼 눌렀을 때
$(".submit-comment").click(function(){
	//console.log('댓글 버튼 클릭');
	//const submitComment = $(this);
	const commetWriter = $(this).siblings('.commetWriter').val();  //댓글 작성자
	console.log(commetWriter)
	
	const inputComment = $(this).siblings('.input-comment').val(); //댓글 내용
	console.log(inputComment)
	
	const tipId = $(this).siblings('.tipId').val(); // 팁 아이디(번호)  
	console.log(tipId)
	
	$.ajax({
		type: 'get', //get, post 중 선택
		url: '/tip/save', //요청을 보냄, 받을때는 컨트롤러에서 받음
		data: { //보내는 데이터
			id: commetWriter, // id => 컨트롤러 함수 매개변수와 똑같이 써줘야 함
            content: inputComment,
            tid: tipId
        },
        contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8', //한글처리
        success: function (result) { // 성공했을 때
        	//alert(result.content);
			console.log(result);
			
			let tip = '<div class="comment-section">'
						+ '<ul class="comments">' 
						+ '<div class="comment-like">'
						+ '<p><span class="memberId">' + result +'</span>'
						+ '<span class="contecnt">'+ inputComment +'</span></p>'
						+ '</div>'
						+ '</ul>'
						+ '<div class="time-log">'
						+ '<span class="tdate">'
						+ '</span>'
						+ '</div>';
			$('.reaction').append(tip);
			
        },
        error: function (err){    // 실패했을 때
           alert('실패')
        }
        
		
	});
	
	
});










// top 버튼----------------------------------------------------------------

	// 보이기 | 숨기기 
	$(window).scroll(function() {
		if ($(this).scrollTop() > 250) {
			//250 넘으면 버튼이 보여짐니다. 
			$('#toTop').fadeIn(); $('#toTop').css('left', $('#sidebar').offset().left); 
			//#sidebar left:0 죄표 
			} else { $('#toTop').fadeOut(); 
			} 
		}); 
	// 버튼 클릭시 
	$("#toTop").click(function() {
		$('html, body').animate({ 
			scrollTop : 0 
			// 0 까지 animation 이동합니다. 
			}, 400); 
		// 속도 400 
		return false; 
		}); 
	});
// top 버튼 끝==============================================================
   


