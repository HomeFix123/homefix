
/*비밀번호 찾기 - 임시비밀번호 이메일로 전송*/
/*$(function(){
		$("#findpw").click(function(){
			$.ajax({
				url : "/sign/member/sendEmail",
				type : "POST",
				data : {
					id : $("#id").val(),
					email : $("#email").val()
				},
				success : function(result) {
					alert(result);
				},
			})
			window.location = "/sign/sign-in";
		});
	})*/
	
	
	$("#findpw").click(function () {
        let email = $("#email").val();
        let id = $("#id").val();

        $.ajax({
            type: "GET",
            url: "/sign/member/sendEmail",
            data: {
                "email": email,
                "id": id
            },
            success: function (res) {
                if (res['check']) {
                    swal("발송 완료!", "입력하신 이메일로 임시비밀번호가 발송되었습니다.", "success").then((OK) => {
                        if(OK) {
                            $.ajax({
                                type: "GET",
                                url: "/sign/check/findPw/sendEmail",
                                data: {
                                    "email": email,
                						"id": id
                                }
                            })
                            window.location = "/sign/sign-in";
                        }


                    }
                )
                    $('#checkMsg').html('<p style="color:darkblue"></p>');
                } else {
                    $('#checkMsg').html('<p style="color:red">일치하는 정보가 없습니다.</p>');
                }
            }
        })
    })