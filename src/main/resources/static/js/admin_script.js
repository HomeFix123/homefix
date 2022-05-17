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
	
	$(document).on('click', '#example tbody tr', function(){
		const cid = $(this).find('.companyId').text();
		detailInfo(cid)
		myModal.show();
	});
	
	
	function detailInfo(id){
		
		companyDetail = null;
		$.ajax({
			type: "GET",
			url : "/admin/company/"+id,
			contentType: 'application/json;charset=utf-8',
			success : success,
			error : function(err){
				console.log(err)
			}			
		})
		
		function success(result){
			
			coInfo = result.company;
			buttons = $('#modal-buttons')
			buttons.find('.company-update').attr('href', '/admin/company/form/' + coInfo.cid)
			
			
			$('.co_name').text(coInfo.co_name);
			default_info = $('#default-info')
			default_info.find('.company_id').text(coInfo.cid);
			default_info.find('.co_logo').attr('src', 'http://140.238.11.118:5000/upload/'+coInfo.co_logo)
			default_info.find('.co_addr').text(coInfo.co_addr);
			default_info.find('.co_num').text(coInfo.co_num);
			default_info.find('.co_ceo').text(coInfo.co_ceo);
			default_info.find('.co_tel').text(coInfo.co_tel);
			default_info.find('.co_email').text(coInfo.co_email);
			default_info.find('.contract').text(coInfo.contract);
			default_info.find('.prefer').text(coInfo.prefer);
			
			
			
			
			detail = $('#detail-info')
			detail.find('.career').text(result.career + '년');
			spe = detail.find('.specialty')
			sp_cont = [];
			for(let i = 0; i < result.specialty.length; i++){
				sp_cont.push(result.specialty[i].sp_cont);
			}
			spe.text(sp_cont.join(", "));
			are = detail.find('.area')
			areas = [];
			for(let i = 0; i < result.area.length; i++){
				areas.push(result.area[i].dname);
			}
			are.text(areas.join(", "));
			detail.find('.area').text(result.area);
			detail.find('.company-img').attr('src','http://140.238.11.118:5000/upload/'+result.cinfo_img);				
		}
		
		
	}
	
	
});

