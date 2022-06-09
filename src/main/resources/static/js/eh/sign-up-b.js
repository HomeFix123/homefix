$(function() {

	/*********[ 사업자 회원가입 페이지 ]*************************************************************/
	/*유효성 검사 정규식*/

	var RegexId = /^[a-zA-z0-9]{4,12}$/; //아이디는 영문 대소문자와 숫자 4~12자리
	//업체명
	var RegexCompany = /^[가-힣a-zA-Z0-9]{1,10}$/;
	var RegexEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var RegexPW = /^(?=.*[a-zA-z])(?=.*[0-9]).{4,10}$/; //4 ~ 10자 영문, 숫자 최소 한가지씩 조합
	var RegexName = /^[가-힣]+$/;
	//전화번호
	var RegexTel = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
	//사업자번호
	var RegexCompanyNum = /^(\d{3,3})+[-]+(\d{2,2})+[-]+(\d{5,5})$/;

	// 에러박스 문구
	var blank = "  필수 입력 사항입니다.";

	//카카오 api 우편번호 찾기
	$('#bs_btn_postCheak').click(function() {
		new daum.Postcode({
			oncomplete: function(data) { //선택시 입력값 세팅
				console.log(data);
				document.getElementById("bs_firstAddress").value = data.address; // 주소 넣기
				document.getElementById("bs_postNum").value = data.zonecode; // 주소 넣기
				document.querySelector("input[name=addrd").focus(); //상세입력 포커싱
			}
		}).open();
	})




	//아이디 중복 클릭이벤트
	var idCheak = false;

	$('#bs_btn_idCheak').click(function() {
		// 아이디 중복검사 확인 여부
		$('label[for="bs_memberId"] .error_box').html("");
		var memberId = $("#bs_memberId").val();

		// 입력값이 없을 때 에러박스
		if (memberId == '') {
			$('label[for="bs_memberId"] .error_box').html(blank);
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexId.test(memberId)) {
			$('label[for="bs_memberId"] .error_box').html("아이디 형식이 올바르지 않습니다.");
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			return;
		}

		// 아이디  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/company/idCheck',
			data: { id: $('#bs_memberId').val() },

			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				console.log(result)
				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('label[for="bs_memberId"] .error_box').css('color', '#4ABA99');
					$('label[for="bs_memberId"] .error_box').html("사용 가능한 아이디입니다.");
					idCheak = true;
				} else {
					$('label[for="bs_memberId"] .error_box').css('color', '#ED7A64');
					$('label[for="bs_memberId"] .error_box').html("사용할 수 없는 아이디입니다.");
					idCheak = false;
				}
			},
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#btn_idCheak').click	



	//사업자번호 중복 이벤트
	var bsNumCheak = false;

	$('#bs_btn_businessNum').click(function() {
		// 사업자번호 중복검사 확인 여부
		$('label[for="bs_businessNum"] .error_box').html("");
		var bs_businessNum = $("#bs_businessNum").val();
		// 입력값이 없을 때 에러박스
		if (bs_businessNum == '') {
			$('label[for="bs_businessNum"] .error_box').html(blank);
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexCompanyNum.test(bs_businessNum)) {
			$('label[for="bs_businessNum"] .error_box').html("사업자 번호 형식이 올바르지 않습니다. 000-00-00000 형식으로 입력하세요");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			return;
		}

		// 사업자번호  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/company/companyNumberCheck',
			data: { num: $('#bs_businessNum').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {

				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('label[for="bs_businessNum"] .error_box').css('color', '#4ABA99');
					$('label[for="bs_businessNum"] .error_box').html("사용 가능한 사업자번호입니다.");
					bsNumCheak = true;
				} else {
					$('label[for="bs_businessNum"] .error_box').css('color', '#ED7A64');
					$('label[for="bs_businessNum"] .error_box').html("사용할 수 없는 사업자번호입니다.");
					bsNumCheak = false;
				}
			},
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#bs_btn_businessNum').click	







	//이메일 중복 버튼 클릭 이벤트
	var emailCheak = false;
	$('#bs_btn_emailCheak').click(function() {
		// 이메일 중복검사 확인 여부
		$('label[for="bs_memberEmail"] .error_box').html("");
		var memberEmail = $("#bs_memberEmail").val();
		// 입력값이 없을 때 에러박스
		if (memberEmail == '') {
			$('label[for="bs_memberEmail"] .error_box').html(blank);
			$('label[for="bs_memberEmail"] .error_box').css('color', '#dc3545');
			return false;
		}

		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexEmail.test(memberEmail)) {
			$('label[for="bs_memberEmail"] .error_box').css('color', '#dc3545');
			$('label[for="bs_memberEmail"] .error_box').html("이메일 형식이 올바르지 않습니다.");
			return;
		}

		// 이메일 중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/company/emailCheck',
			data: { email: $('#bs_memberEmail').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('label[for="bs_memberEmail"] .error_box').css('color', '#4ABA99');
					$('label[for="bs_memberEmail"] .error_box').html("사용 가능한 이메일입니다.");
					emailCheak = true;
				} else {
					$('label[for="bs_memberEmail"] .error_box').css('color', '#ED7A64');
					$('label[for="bs_memberEmail"] .error_box').html("사용할 수 없는 이메일입니다.");
					emailCheak = false;
				}
			},
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#btn_emailCheak').click


	/***********************************************************
	 사업가 회원가입 */


	$('#bs_btn_signUp').click(function() {

		// input에 입력된 값을 공백제거하고 변수에 담기
		var memberEmail = $("#bs_memberEmail").val();
		var memberPassword = $("#bs_memberPassword").val();
		var passwordCheck = $("#bs_passwordCheck").val();
		var memberName = $("#bs_memberName").val();
		var bsName = $("#bs_Name").val();
		var memberTel = $("#bs_memberTel").val();
		var postNum = $("#bs_postNum").val();
		var bsBusinessNum = $("#bs_businessNum").val();
		var memberId = $("#bs_memberId").val();
		var secondAddress = $("#bs_secondAddress").val();


		/*아이디*/
		if (memberId == '') {
			$('label[for="bs_memberId"] .error_box').html(blank);
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			$('#bs_memberId').focus();
			return false;
		} else {
			$('label[for="bs_memberId"] .error_box').html("");
		}

		if (!RegexId.test(memberId)) {
			$('label[for="bs_memberId"] .error_box').html("아이디 형식이 올바르지 않습니다.");
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			$('#bs_memberId').focus();
			return false;
		} else {
			$('label[for="bs_memberId"] .error_box').html("");
		}
		if(idCheak == false){
			$('label[for="bs_memberId"] .error_box').html("아이디 중복검사를 하지 않았습니다.");
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			
			return false ;
		}

		/*업체명*/

		if (bsName == '') {
			$('label[for="bs_Name"] .error_box').html(blank);
			$('label[for="bs_Name"] .error_box').css('color', '#dc3545');
			$('#bs_Name').focus();
			return false;
		} else {
			$('label[for="bs_Name"] .error_box').html("");

		}

		if (!RegexCompany.test(bsName)) {
			$('label[for="bs_Name"] .error_box').html("한글, 영문 그리고 숫자만 입력 가능합니다.");
			$('label[for="bs_Name"] .error_box').css('color', '#dc3545');
			$('#bs_Name').focus();
			return false;
		} else {
			$('label[for="bs_Name"] .error_box').html("");

		}



		/* 대표명 */
		if (memberName == '') {
			$('label[for="bs_memberName"] .error_box').html(blank);
			$('label[for="bs_memberName"] .error_box').css('color', '#dc3545');
			$('#bs_memberName').focus();
			return false;
		} else {
			$('label[for="bs_memberName"] .error_box').html("");

		}
		if (!RegexName.test(memberName)) {

			$('label[for="bs_memberName"] .error_box').html("한글만 입력 가능합니다.");
			$('label[for="bs_memberName"] .error_box').css('color', '#dc3545');
			$('#bs_memberName').focus();
			return false;
		} else {
			$('label[for="bs_memberName"] .error_box').html("");
		}

		/* 사업자 번호 */
		if (bsBusinessNum == '') {
			$('label[for="bs_businessNum"] .error_box').html(blank);
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			$('#bs_businessNum').focus();
			return false;
		} else {
			$('label[for="bs_businessNum"] .error_box').html("");

		}
		if (!RegexCompanyNum.test(bsBusinessNum)) {

			$('label[for="bs_businessNum"] .error_box').html("숫자 10글자를 입력해주세요.");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			$('#bs_businessNum').focus();
			return false;
		} else {
			$('label[for="bs_businessNum"] .error_box').html("");
		}
		if (bsNumCheak == false) {
			$('label[for="bs_businessNum"] .error_box').html("사업자번호 중복검사를 하지 않았습니다.");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');

			return false;
		}



		/* 비밀번호 */
		if (memberPassword == '') {
			$('label[for="bs_memberPassword"] .error_box').html(blank);
			$('label[for="bs_memberPassword"] .error_box').css('color', '#dc3545');
			$('#bs_memberPassword').focus();
			return false;
		} else {
			$('label[for="bs_memberPassword"] .error_box').html("");
		}

		if (!RegexPW.test(memberPassword)) {

			$('label[for="bs_memberPassword"] .error_box').html("4 ~ 10자 영문, 숫자를 최소 한가지씩 조합하여 만들어 주십시오.");
			$('label[for="bs_memberPassword"] .error_box').css('color', '#dc3545');
			$('#bs_memberPassword').focus();
			return false;
		} else {
			$('label[for="bs_memberPassword"] .error_box').html("");
		}

		/* 비밀번호 재확인 */
		if (passwordCheck == '') {
			$('label[for="bs_passwordCheck"] .error_box').html("필수 입력 사항입니다.");
			$('label[for="bs_passwordCheck"] .error_box').css('color', '#dc3545');
			$('#bs_passwordCheck').focus();
			return false;
		} else {
			$('label[for="bs_passwordCheck"] .error_box').html("");
		}


		/* 비밀번호 일치 여부 확인 */
		if (memberPassword != passwordCheck) {
			$('label[for="bs_passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
			$('label[for="bs_passwordCheck"] .error_box').css('color', '#dc3545');
			$('#bs_passwordCheck').focus();
			return false;
		}

		/*우편번호 */
		if (postNum == '' || secondAddress == '') {
			$('label[for="bs_postNum"] .error_box').html(blank);
			$('label[for="bs_postNum"] .error_box').css('color', '#dc3545');
			$('#bs_secondAddress').focus();
			return false;
		}

		/* 이메일 */
		if (memberEmail == '') {
			$('label[for="bs_memberEmail"] .error_box').html(blank);
			$('label[for="bs_memberEmail"] .error_box').css('color', '#dc3545');
			$('#bs_memberEmail').focus();
			return false;
		} else {
			$('label[for="bs_memberEmail"] .error_box').html("");
		}

		if (!RegexEmail.test(memberEmail)) {
			$('label[for="bs_memberEmail"] .error_box').html("이메일 형식이 올바르지 않습니다.");
			$('label[for="bs_memberEmail"] .error_box').css('color', '#dc3545');
			$('#bs_memberEmail').focus();
			return false;
		} else {
			$('label[for="bs_memberEmail"] .error_box').html("");
		}


		if (emailCheak == false) {
			$('label[for="bs_memberEmail"] .error_box').html("이메일 중복검사를 하지 않았습니다.");
			$('label[for="bs_memberEmail"] .error_box').css('color', '#dc3545');

			return false
		};

		/* 전화번호 */
		if (memberTel == '') {
			$('label[for="bs_memberTel"] .error_box').html(blank);
			$('label[for="bs_memberTel"] .error_box').css('color', '#dc3545');
			$('#bs_memberTel').focus();
			return false;
		} else {
			$('label[for="bs_memberTel"] .error_box').html("");
		}


		if (!RegexTel.test(memberTel)) {

			$('label[for="bs_memberTel"] .error_box').html("전화번호 형식이 올바르지 않습니다.");
			$('label[for="bs_memberTel"] .error_box').css('color', '#dc3545');
			return false;
		} else {
			$('label[for="bs_memberTel"] .error_box').html("");
		}

	});



	/************필수 동의 사항******************************************** */

	$('#btnAgree').click(function() {
		$('#agreeForm').toggle();
	});

	$('#btnChoiceAgree').click(function() {
		$('#agreeChoiceForm').toggle();
	});

	$("#allCheck").click(function() {
		if ($("input:checkbox[id='allCheck']").is(":checked") == true) {

			$('#termsService').prop("checked", true);
			$('#choicCheck').prop("checked", true);

		} else {
			$('#termsService').prop("checked", false);
			$('#choicCheck').prop("checked", false);
		}
	})

	$('#termsService').click(function() {
		if ($('#termsService').is(":checked") == false | $('#choicCheck').is(":checked") == false) {
			$('#allCheck').prop("checked", false);
		} else {
			$('#allCheck').prop("checked", true);

		}
	})

	$('#choicCheck').click(function() {
		if ($('#termsService').is(":checked") == false | $('#choicCheck').is(":checked") == false) {
			$('#allCheck').prop("checked", false);
		} else {
			$('#allCheck').prop("checked", true);

		}
	})



	$('#btn_termCheck').click(function() {
		if ($('#termsService').is(":checked") == false) {
			$('.check_error_box').html("<h6>필수 이용 약관에 동의해주세요.</h6>");
			$('.check_error_box').css('color', '#dc3545');
			return;

		} else {

			location.href = "/sign/company/sign-up-b"

		}

	})

	$('#btn_signCancel').click(function() {
		location.href = "/sign/sign-in"
	})

	/*로그인 아이디 비밀번호 틀림*/
	if ($('#errorBox').text() == 'N') {
		$('#errorBoxReal').text("아이디 또는 비밀번호를 잘못 입력하셨습니다.").css("color", "red")
	}



//비밀번호 찾기

$("#cfindpw").click(function() {
	let email = $("#email").val();
	let id = $("#id").val();
	$.ajax({
		type: "get",
		url: "/sign/checkFindPw",
		data: {
			"email": email,
			"id": id
		},
		success: function(res) {
			//alert(res);
			//console.log(res);
			if (res['check']) {

				swal({title:"발송 완료!",text: "입력하신 이메일로 임시비밀번호가 발송되었습니다.",icon: "success"},function(OK){
					if(OK){
					$.ajax({
						type: "POST",
						url: "/sign/checkSendEmail",
						data: {
							"email": email,
							"id": id
						}
					})//ajax
				//	alert(email);
				//	alert(id);
					window.location = "/sign/sign-in";
					}//if
					
                })//swal
                }// if
            }
        })// ajaz
    })//click


//아이디 찾기

$("#cfindid").click(function() {
	let ceo = $("#ceoName").val();
	let tel = $("#ceoTel").val();
	console.log(name+"/"+tel)
	$.ajax({
		type: "get",
		url: "/sign/checkFindId",
		data: {
			"ceo": ceo,
			"tel": tel
		},
		success: function(res) {
			alert("아이디는 "+res+"입니다.");
			console.log(res);
			
            }
        })// ajaz
    })//click



})