var col_kor = [
            { title: "아이디" },
            { title: "업체명" },
            { title: "상담수" },
            { title: "계약수" },
            { title: "월정액 잔여일" },
            { title: "신고수" }
        ];
        
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
			buttons = $('#modal-buttons');
			buttons.find('.company-update').attr('href', '/admin/company/form/' + coInfo.id);
			buttons.find('.company-blacklist').val(coInfo.enabled);
			if(coInfo.enabled){
				buttons.find('.company-blacklist').html("계정 정지");	
			} else {
				buttons.find('.company-blacklist').html("정지 해제");
			}
			
			$('.co_name').text(coInfo.name);
			default_info = $('#default-info');
			default_info.find('.company_id').text(coInfo.id);
			default_info.find('.co_logo').attr('src', 'http://140.238.11.118:5000/upload/'+coInfo.logo)
			default_info.find('.co_addr').text(coInfo.addr);
			default_info.find('.co_num').text(coInfo.num);
			default_info.find('.co_ceo').text(coInfo.ceo);
			default_info.find('.co_tel').text(coInfo.tel);
			default_info.find('.co_email').text(coInfo.email);
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
				if(result.area[i].area.dt_area){
					
					areas.push(result.area[i].area.aname + " " + result.area[i].area.dt_area);
				} else {
					areas.push(result.area[i].area.aname);	
				}	
			}
			are.text(areas.join(", "));
			
			detail.find('.company-img').attr('src','http://140.238.11.118:5000/upload/'+result.cinfo_img);				
		}
	}
	
	
});

$(function(){
	function handleBlacklist(blacklist){
		const cid = $('#default-info .company_id').text()
		$.ajax({
			type: "PUT",
			url: "/admin/company/blacklist/" + cid,
			data : {"enabled" : blacklist}
			
		});
	}
	
	$('#modal-buttons .company-blacklist').click(function(){
		if($(this).attr('value') == "true"){
			$(this).html('정지 해제');
			handleBlacklist(false);
			$(this).attr('value', "false")	
		} else {
			$(this).html('계정 정지');
			handleBlacklist(true);
			$(this).attr('value', "true")
		}
		
		
		
	})
})


