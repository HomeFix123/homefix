var col_kor = [
	{"title": "아이디"},
	{"title": "닉네임"},
	{"title": "이름"},
	{"title": "가입일"},
	{"title": "최근 접속일"},
	{"title": "신고수"},
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
	
	let memberData;
	let isBlacklist;
	
	$(document).on('click', '#example tbody tr', function(){
		const id = $(this).find('.id').text();
		memberData = searchInfo(id);
		
		if(memberData.profilimg == undefined){
			profileimg.attr('src', 'http://140.238.11.118:5000/upload/profile_basic.png');
		} else {
			profileimg.attr('src', memberData.profilimg);	
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
		
		isBlacklist = !memberData.enabled;
		blacklistBtn.val(isBlacklist);
		blacklistBtn.text(isBlacklist ? '정지 해제' : '계정 정지');
		
		
		myModal.show();
	});
	
	
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