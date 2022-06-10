/*회원탈퇴*/

$('#Withdrawal').click(function() {
	var result = confirm("정말 탈퇴하시겠습니까?");
	if (result) {
		const id = $('#id').val();
		const password = $('#password').val();
		alert(id+"/"+password);

		if (password != "") {
		alert("패스워드 넘어왔는가?"+password);
			$.ajax({
				url: "/sign/memberSecession",
				data: { pass: $('#password').val() },
				contentType: 'application/x-www-form-urlencoded;charset=utf-8', //스트링 타입으로 보내기 포스트형식으로 보내면 안됨
				type: 'delete',//전송 타입을 Delete로 중요!
				success: function(result) {
					console.log(result);
					if (result == 'Y') {
						console.log(result)
						alert("탈퇴에 성공하였습니다.")
						location.href = "/sign/sign-in";
					} else {	//if end
						alert("비밀번호가 틀립니다.")
						alert("실패!!! 패스워드 넘어왔는가?"+password);
					}	//else end
				},	//success end
				error: function(err) {
					console.log(err);
				} //error end
			});	//ajax end
		} else {	//if (password != "") end
			alert("비밀번호를 입력하세요.");
		} //else end

	} //if end

})	//#Withdrawal . click end

/*******************************************************************/

