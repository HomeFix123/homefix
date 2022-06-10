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
	
	const reaction = $(this).parents('.comment').siblings('.reaction'); //해당 댓글 찾기
	
	// 댓글 입력 에이작스================================================================
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
			if(result.member.profilimg == null){
				let tip = '<div class="comment-section">'
						+ '<ul class="comments">' 
						+ '<div class="comment-like">'
						+ '<p>'
						+ '<img src="https://cdn-icons-png.flaticon.com/512/3135/3135789.png" alt="Image" class="img-fluid" width="20px"/>'
						+ '<span class="memberId" style="font-weight:bold">' + result.member.nickname +'</span> &nbsp;'
						+ '<span class="contecnt">'+ inputComment +'</span> &nbsp;'
						+ '<button type="button" class="delete-comment" value='+ result.cmid  +'><i class="fas fa-trash-alt fa-xs"></i></button>'
						+ '</p>'
						+ '</div>'
						+ '</ul>'
						+ '<div class="time-log">'
						+ '<span class="tdate">'
						+ '</span>'
						+ '</div>';
						
			reaction.append(tip); // 해당 댓글에 댓글 입력하기
			}else {
				let tip = '<div class="comment-section">'
						+ '<ul class="comments">' 
						+ '<div class="comment-like">'
						+ '<p>'
						+ '<img src="http://140.238.11.118:5000/upload/'+result.member.profilimg + '" alt="Image" class="img-fluid" width="20px">'
						+ '<span class="memberId" style="font-weight:bold">' + result.member.nickname +'</span> &nbsp;'
						+ '<span class="contecnt">'+ inputComment +'</span> &nbsp;'
						+ '<button type="button" class="delete-comment" value='+ result.cmid  +'><i class="fas fa-trash-alt fa-xs"></i></button>'
						+ '</p>'
						+ '</div>'
						+ '</ul>'
						+ '<div class="time-log">'
						+ '<span class="tdate">'
						+ '</span>'
						+ '</div>';
						
			reaction.append(tip); // 해당 댓글에 댓글 입력하기
			}
			
			
			//window.location.reload();
			
        },
        error: function (err){    // 실패했을 때
           alert('실패')
        }
		
	});
	//================================================================
	
	// 댓글등록시 댓글 입력란 비우기
	$(this).siblings('.input-comment').val("");
	
});


// 댓글 삭제 눌렀을 때
$(document).on("click", ".delete-comment", function(){
	const cmid = $(this).val()
	console.log(cmid);
	console.log('댓글삭제 버튼 클릭');
	alert('댓글을 정말 삭제하시겠습니까?')
	
	// 댓글 삭제 에이작스================================================================
	$.ajax({
		type: 'DELETE', // delete는 삭제 요청
		url: '/tip/delete/'+cmid, //요청을 보냄, 받을때는 컨트롤러에서 받음
		data: { //보내는 데이터
			cmid : cmid
        },
        contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8', //한글처리
        success: function (result) { // 성공했을 때
			//alert(result)
			// 댓글 삭제 후 페이지 재로딩
			window.location.reload();
        },
        error: function (err){    // 실패했을 때
           alert('실패')
        }
		
	});
	//================================================================



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


})