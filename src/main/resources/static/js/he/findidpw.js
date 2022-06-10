
/*비밀번호 찾기 - 임시비밀번호 이메일로 전송*/

$("#findpw").click(function() {
	let email = $("#email").val();
	let id = $("#id").val();

	$.ajax({
		type: "GET",
		url: "/sign/check/findPw",
		data: {
			"email": email,
			"id": id
		},
		success: function(res) {
			//alert(res);
			console.log(res);
			if (res['check']) {

				swal({title:"발송 완료!",text: "입력하신 이메일로 임시비밀번호가 발송되었습니다.",icon: "success"},function(OK){
					if(OK){
					$.ajax({
						type: "POST",
						url: "/sign/check/findPw/sendEmail",
						data: {
							"email": email,
							"id": id
						}
					})//ajax
					//alert(email);
					//alert(id);
					window.location = "/sign/sign-in";
					}//if
					
                })//swal
                }// if
            }
        })// ajaz
    })//click
    
/*아이디 찾기 - 이메일/전화번호 매치*/
//아이디 찾기

$("#findid").click(function() {
	let email = $("#email").val();
	let tel = $("#tel").val();
	console.log(email+"/"+tel)
	$.ajax({
		type: "get",
		url: "/sign/check/FindId",
		data: {
			"email": email,
			"tel": tel
		},
		success: function(res) {
			alert("아이디는 "+res+"입니다.");
			console.log(res);
			
            }
        })// ajaz
    })//click

