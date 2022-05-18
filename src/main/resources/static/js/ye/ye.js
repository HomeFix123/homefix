$(function() {
			$(".progress_space").on("click", function() {
//				$("#progress_detail").css("display", "block");
                let offset = $(".progress_detail").offset();
                $('html,body').animate({scrollTop : offset.top},400);
			});
    
            $(".complete_btn.btn.btn-primary").unbind('mouseenter mouseleave');
		});

//$(document).ready(function() {
//
//	함수 코드				
//
//});