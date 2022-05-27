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




