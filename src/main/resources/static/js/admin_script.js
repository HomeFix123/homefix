// 네비게이션바 관련
$(function(){
	const navbar = $('ul#admin-nav');
	const nav_text = $('#admin-nav > li > a > span')
	
	const info_text = $('#admin-info .text > p')
	const info_icon = $('#admin-info .icon')
	$(window).resize(function(){
		let width = window.innerWidth;
		// md 사이즈
	    if(width < 992){
	    	navbar.removeClass('flex-column');
	    } else {
	        navbar.addClass('flex-column');
	    }
	    
	    // sm 사이즈    	
	    if(width < 768){
	        nav_text.hide();
	        
	    } else {
	    	nav_text.show();
	    }
	    
	    // xs 사이즈
	    if(width < 576){
	        info_text.hide();
	        info_icon.width('60px');
	        info_icon.height('60px');
	        info_icon.css('line-height', '60px')
	    } else {
	    	info_text.show();
	        info_icon.width('80px');
	        info_icon.height('80px');
	        info_icon.css('line-height', '80px')
	    }
	        	
	}).resize();
	
	
	
});

// 데이터 테이블
$(function(){
        $(document).ready(function () {
            $('#example').DataTable({
            	data: dataSet,
            	columns: col_kor,
                language: kor_setting
            });
        });
        
        const kor_setting = {
        	"decimal" : "",
        	"emptyTable" : "데이터가 없습니다.",
        	"info" : "_START_ - _END_ (총 _TOTAL_ 명)",
        	"infoEmpty" : "0명",
            "infoFiltered" : "(전체 _MAX_ 명 중 검색결과)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "_MENU_ 개씩 보기",
            "loadingRecords" : "로딩중...",
            "processing" : "처리중...",
            "search" : "검색 : ",
            "zeroRecords" : "검색된 데이터가 없습니다.",
            "paginate" : {
            	"first" : "첫 페이지",
            	"last" : "마지막 페이지",
            	"next" : "다음",
            	"previous" : "이전"
            },
            "aria" : {
            	"sortAscending" : " :  오름차순 정렬",
            	"sortDescending" : " :  내림차순 정렬"
        	}
        };
});

// 모달창
$(function(){
	var myModal = new bootstrap.Modal(document.getElementById('info-modal'), {
		keyboard: false
	});
	
	$(document).on('click', '#example tr', function(){
		
		myModal.show();
	});
});

