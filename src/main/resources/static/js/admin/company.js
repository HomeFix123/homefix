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
	
	$(document).on('click', '#reports a.info', function(event){
		event.preventDefault();
		const cid = $(this).attr('value');
		detailInfo(cid)
		myModal.show();
	});
	
	
	function detailInfo(id){
		
		companyDetail = null;
		$.ajax({
			type: "GET",
			url : "/admin/company/"+id,
			contentType: 'application/json;charset=utf-8',
			error : function(err){
				console.log(err)
			}			
		}).done((result) =>{
			const coInfo = result.company;
			
			$('.co_name').text(coInfo.name);
			
			const buttons = $('#modal-buttons');
			const updateBtn = buttons.find('.company-update');
			const blacklistBtn = buttons.find('.company-blacklist');
			
			updateBtn.attr('href', '/admin/company/form/' + coInfo.id);
			blacklistBtn.val(coInfo.enabled);
			
			
			
			const default_info = $('#default-info');
			default_info.find('.company_id').text(coInfo.id);
			default_info.find('.co_logo').attr('src', 'http://140.238.11.118:5000/upload/'+coInfo.logo)
			default_info.find('.co_addr').text(coInfo.addr);
			default_info.find('.co_num').text(coInfo.num);
			default_info.find('.co_ceo').text(coInfo.ceo);
			default_info.find('.co_tel').text(coInfo.tel);
			default_info.find('.co_email').text(coInfo.email);
			default_info.find('.contract').text(coInfo.contract);
			default_info.find('.prefer').text(coInfo.prefer);
			
			
			
			
			const detail = $('#detail-info')
			const speTd = detail.find('.specialty')
			const areTd = detail.find('.area')
			
			detail.find('.career').text(result.career + '년');
			
			let sp_cont = [];
			for(let i = 0; i < result.specialty.length; i++){
				sp_cont.push(result.specialty[i].sp_cont);
			}
			speTd.text(sp_cont.join(", "));
			
			let areas = [];
			for(let i = 0; i < result.area.length; i++){
				if(result.area[i].area.dt_area){
					
					areas.push(result.area[i].area.aname + " " + result.area[i].area.dt_area);
				} else {
					areas.push(result.area[i].area.aname);	
				}	
			}
			areTd.text(areas.join(", "));
			
			const companyImg = detail.find('.company-img')
			companyImg.attr('src','http://140.238.11.118:5000/upload/'+result.cinfo_img);
			
			
			const paymentTable = $('#payment-table')
			const paymentTbody = paymentTable.find('tbody');
			const paymentTfoot = paymentTable.find('tfoot');
			paymentTbody.children().remove();
			
			const payList = searchPayList(id);
			
			
			let total = 0;
			for(let payLog of payList){
				let tr = "<tr>";
				tr += "<td>" + new Date(payLog.pday).toLocaleDateString() + "</td>";
				tr += "<td>" + payLog.pid + "</td>";
				tr += "<td class='text-center'>" + payLog.method + "</td>";
				tr += "<td class='text-end'>" + payLog.amount.toLocaleString() + "원</td>";
				tr += "</tr>";
				paymentTbody.append(tr);
				total += (payLog.amount);
			}
			
			paymentTfoot.find("td.payment-sum").text(total.toLocaleString() + '원');	
			
							
		})
	}
	// 결제 목록 검색
	function searchPayList(id){
		let result = null;
		$.ajax({
			type: "GET",
			url: "/admin/company/payment/" + id,
			contentType: "application/json",
			async: false
		}).done((data) => {
			
			result = data;
			
		}).fail((err) => console.log(err))
		return result;
	}
	
});

$(function(){
	// 블랙리스트 변경
	function changeBlacklist(blacklist){
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
			changeBlacklist(false);
			$(this).attr('value', "false")	
		} else {
			$(this).html('계정 정지');
			changeBlacklist(true);
			$(this).attr('value', "true")
		}
	})
})

$(function(){
	$('#reports tr a.btn-danger').click(function(){
		const reportId = $(this).attr('value');
		
		$.ajax({
			type: "DELETE",
			url: "/admin/company/report/" + reportId
		}).done(() => {
			
		}).fail((err) => {
			console.log(err);
		})
	})
})


