var col_kor = [
	{"title": "아이디"},
	{"title": "닉네임"},
	{"title": "이름"},
	{"title": "가입일"},
]
$(function(){
	
	var myModal = new bootstrap.Modal(document.getElementById('info-modal'), {
		keyboard: false
	});
	
	const profileimg = $("#profileimg");
	
	const modalTable = $('#modal-info');
	const trId = modalTable.find('.id');
	const trNick = modalTable.find('.nickname');
	const trSubdate = modalTable.find(".subdate");
	const trName = modalTable.find(".name");
	const trTel = modalTable.find(".tel");
	const trEmail = modalTable.find(".email");
	const trBday = modalTable.find(".bday");
	const trAge = modalTable.find(".age")
	const trAddr = modalTable.find(".addr");
	const trAddrd = modalTable.find(".addr-d");
	
	const modalButtons = $('#modal-buttons');
	const modifyBtn = modalButtons.find('.modify');
	const blacklistBtn = modalButtons.find(".blacklist");
	
	const estTable = $('#estTable');
	const estTbody = estTable.find('tbody');
	
	const reportTable = $('#reportTable');
	const reportTbody = reportTable.find('tbody'); 
	
	let memberData;
	let memberEstData;
	let memberReportData;
	let isBlacklist;
	
	const imgURL = 'http://140.238.11.118:5000/upload/'
	const defaultImg = 'profile_basic.png'
	
	function showMemberModal(id){
		memberData = searchInfo(id);
		if(memberData.profilimg == undefined){
			profileimg.attr('src', imgURL + defaultImg);
		} else {
			profileimg.attr('src', imgURL + memberData.profilimg);	
		}
		
		
		trId.text(memberData.id);
		trNick.text(memberData.nickname);
		trSubdate.text(new Date(memberData.subdate).toLocaleDateString());
		trName.text(memberData.name);
		trTel.text(memberData.tel);
		trEmail.text(memberData.email);
		trBday.text(new Date(memberData.bday).toLocaleDateString());
		trAge.text(new Date().getFullYear() - new Date(memberData.bday).getFullYear());
		trAddr.text(memberData.addr);
		trAddrd.text(memberData.addrd);
		
		// 블랙리스트 설정
		isBlacklist = !memberData.enabled;
		blacklistBtn.val(isBlacklist);
		blacklistBtn.text(isBlacklist ? '정지 해제' : '계정 정지');
		
		// 견적 목록
		memberEstData = searchEstById(id);
		console.log(memberEstData);
		estTbody.empty();
		for(let est of memberEstData){
			let tr = "<tr>"
			tr += "<td>"+ est.eid + "</td>"
			tr += "<td>"
			tr += "<a>" + est.building + "/" +est.space + "/" + est.size + "평</a>"
			tr += "</td>"
			tr += "<td>"+ est.budget +"만원</td>"
			tr += "</tr>" 
			estTbody.append(tr);
		}
		
		// 신고 목록
		memberReportData = searchReportById(id);
		reportTbody.empty();
		
		for(let report of memberReportData){
			let tr = "<tr>"
			tr += "<td>" + new Date(report.rday).toLocaleDateString() + "</td>"
			tr += "<td>" + report.reason + "</td>"
			tr += "</tr>"
			reportTbody.append(tr);	
		}
		reportTable.find('tfoot > tr > td:eq(1)').text(memberReportData.length + '건');
		
		
		
		
		
		myModal.show();
	}
	function searchInfo(id){
		let result = null;
		$.ajax({
			url: "/admin/member/" + id,
			contentType: "application/json",
			async: false
		}).done((data) => {
			result = data;
		}).fail((error) => {
			console.log(error)
		});
		
		return result;
	}
	
	function searchReportById(id){
		let result = null;
		$.ajax({
			type: "GET",
			url: "/admin/member/report/" + id,
			contentType: "application/json",
			async: false
		}).done((data) => {
			result = data;
		}).fail((error) => {
			console.log(error);
		})
		return result;
	}
	
	function searchEstById(id){
		let result = null;
		$.ajax({
			type: "GET",
			url: "/admin/member/est/" + id,
			contentType: "application/json",
			async: false
		}).done((data) => {
			result = data;
		}).fail((error) => {
			console.log(error);
		})
		return result;
	}
	
	$(document).on('click', '#example tbody tr', function(){
		const id = $(this).find('.id').text();
		showMemberModal(id);
	});
	
	modifyBtn.click((event) => {
		event.preventDefault();
		
		location.href = "/admin/member/form/" + memberData.id;
	}) 
	
	
	
	blacklistBtn.click((event) => {
		event.preventDefault();
		
		$.ajax({
			type: "PUT",
			url: "/admin/member/blacklist/" + memberData.id,
			data : {"enabled" : isBlacklist}
		}).done(() => {
			isBlacklist = !isBlacklist;
			blacklistBtn.val(isBlacklist);
			blacklistBtn.text(isBlacklist ? '정지 해제' : '계정 정지');
		});
	})
	
	$(document).on('click', '#reports > tbody > tr > td > a.info', function(event){
		event.preventDefault();
		const id = $(this).attr('value')
		showMemberModal(id);
	})
});


$(function(){
	$('#reports > tbody > tr > td > a.bg-danger').click(function(event){
		event.preventDefault();
		const rid = $(this).attr('value');
		$.ajax({
			type: "DELETE",
			url: '/admin/member/report/' + rid
		}).done((result) => {
			$(this).parent().parent().remove();
		}).fail((err) => {
			console.log(err)
		})
	});
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