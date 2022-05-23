$(function() {

	/*********[ 사업자 회원가입 페이지 ]*************************************************************/
	/*유효성 검사 정규식*/

	var RegexId = /^[a-zA-z0-9]{4,12}$/; //아이디는 영문 대소문자와 숫자 4~12자리
	//업체명
	var RegexCompany = /^[가-힣a-zA-Z0-9]{1,10}$/;
	var RegexEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var RegexPW = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/; //8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합
	var RegexName = /^[가-힣]+$/;
	//전화번호
	var RegexTel = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;


	//사업자번호
	function checkCorporateRegistrationNumber(value) {
		var valueMap = value.replace(/-/gi, '').split('').map(function(item) {
			return parseInt(item, 10);
		});

		if (valueMap.length === 10) {
			var multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
			var checkSum = 0;
			for (var i = 0; i < multiply.length; ++i) {
				checkSum += multiply[i] * valueMap[i];
			}
			checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);
			return Math.floor(valueMap[9]) === (10 - (checkSum % 10));
		}
		return false;
	}


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
		var memberId = $.trim($("#bs_memberId").val());

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
			url: '/idCheck',
			data: { id: $('#bs_memberId').val() },
			
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) 
			{
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
		var bs_businessNum = $.trim($("#bs_businessNum").val());

		// 입력값이 없을 때 에러박스
		if (bs_businessNum == '') {
			$('label[for="bs_businessNum"] .error_box').html(blank);
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!checkCorporateRegistrationNumber(bs_businessNum.replaceAll("-", ""))) {
			$('label[for="bs_businessNum"] .error_box').html("사업자 번호 형식이 올바르지 않습니다.");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			return;
		}

		// 사업자번호  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/companyNumberCheck',
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
		var memberEmail = $.trim($("#bs_memberEmail").val());

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
			url: '/emailCheck',
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
		var memberEmail = $.trim($("#bs_memberEmail").val());
		var memberPassword = $.trim($("#bs_memberPassword").val());
		var passwordCheck = $.trim($("#bs_passwordCheck").val());
		var memberName = $.trim($("#bs_memberName").val());
		var bsName = $.trim($("#bs_Name").val());
		var memberTel = $.trim($("#bs_memberTel").val());
		var postNum = $.trim($("#bs_postNum").val());
		var bsBusinessNum = $.trim($("#bs_businessNum").val());
		var memberId = $.trim($("#bs_memberId").val());
		var secondAddress = $.trim($("#bs_secondAddress").val());




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
		/*if(idCheak == false){
			$('label[for="bs_memberId"] .error_box').html("아이디 중복검사를 하지 않았습니다.");
			$('label[for="bs_memberId"] .error_box').css('color', '#dc3545');
			
			return false ;
		}*/

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
		if (!checkCorporateRegistrationNumber(bsBusinessNum.replaceAll("-", ""))) {

			$('label[for="bs_businessNum"] .error_box').html("숫자 10글자를 입력해주세요.");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			$('#bs_businessNum').focus();
			return false;
		} else {
			$('label[for="bs_businessNum"] .error_box').html("");
		}
		if(bsNumCheak == false){
			$('label[for="bs_businessNum"] .error_box').html("사업자번호 중복검사를 하지 않았습니다.");
			$('label[for="bs_businessNum"] .error_box').css('color', '#dc3545');
			
			return false ;
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

			$('label[for="bs_memberPassword"] .error_box').html("8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하여 만들어 주십시오.");
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


		if(emailCheak == false){
			$('label[for="memberEmail"] .error_box').html("이메일 중복검사를 하지 않았습니다.");
			$('label[for="memberEmail"] .error_box').css('color', '#dc3545');
			
			return false };
		






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



	 




	/*********************************************************************
		[ 비밀번호 재설정 페이지(1) ]
		비밀번호 찾기 버튼 클릭
	*/
	let ramdom;
	$('#btn_emailSend').click(function() {



		// 인증번호 전송
		$.ajax({
			type: 'post',
			url: 'emailSend.do',
			data: {
				memberEmail: $('#memberEmail').val(),
			},
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				ramdom = result;
			},
			error: function(err) {

				alert('실패');
				console.log(err);
			}
		}); //end of ajax

	})


	$('#btnPwSearch').click(function() {

		// input에 입력된 값을 공백제거하고 변수에 담기
		var authenticationNumber = $.trim($("#authenticationNumber").val());

		// 인증번호와 입력한 문자열이 같을 때 폼 전송
		if (ramdom === authenticationNumber) {
			document.pwSearchForm.submit();
		} else {
			$('.error_box.pwSearch').html("인증번호가 일치하지 않습니다.");
		}

	}) // end of #btnPwSearch



	// [ 비밀번호 재설정 페이지(2) ]
	$('#btnPwChange').click(function() {
		var memberPassword = $.trim($("#memberPassword").val());
		var passwordCheck = $.trim($("#passwordCheck").val());

		/* 비밀번호 */
		if (memberPassword == '') {
			$('label[for="memberPassword"] .error_box').html(blank);
			$('#memberPassword').focus();
			return;
		} else {
			$('label[for="memberPassword"] .error_box').html("");
		}

		if (!RegexPW.test(memberPassword)) {

			$('label[for="memberPassword"] .error_box').html("비밀번호는 영문자와 숫자를 사용하여 6~15자로 작성해 주십시오.");
			return;
		} else {
			$('label[for="memberPassword"] .error_box').html("");
		}

		/* 비밀번호 재확인 */
		if (passwordCheck == '') {
			$('label[for="passwordCheck"] .error_box').html("필수 입력 사항입니다.");
			$('#passwordCheck').focus();
			return;
		} else {
			$('label[for="passwordCheck"] .error_box').html("");
		}


		/* 비밀번호 일치 여부 확인 */
		if (memberPassword != passwordCheck) {
			$('label[for="passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
			$('#passwordCheck').focus();
			return;
		}
		document.pwChangeForm.submit();
		alert("비밀번호가 변경되었습니다.");

	}); // end of #btnPwChange


	$('#btnMemberUpdate').click(function() {

		// input에 입력된 값을 공백제거하고 변수에 담기
		var memberNickname = $.trim($("#memberNickname").val());
		var memberPassword = $.trim($("#memberPassword").val());
		var passwordCheck = $.trim($("#passwordCheck").val());
		var memberName = $.trim($("#memberName").val());
		var memberBirth = $.trim($("#memberBirth").val());
		var memberTel = $.trim($("#memberTel").val());


		/* 업체명 */
		if (memberNickname == '') {
			$('label[for="memberNickname"] .error_box').html(blank);
			$('#memberNickname').focus();
			return;
		} else {
			$('label[for="memberNickname"] .error_box').html("");
		}

		if (!RegexNick.test(memberNickname)) {

			$('label[for="memberNickname"] .error_box').html("닉네임 형식이 올바르지 않습니다.");
			return;
		} else {
			$('label[for="memberNickname"] .error_box').html("");
		}

		if (memberPassword != "") {

			/* 비밀번호 */

			if (!RegexPW.test(memberPassword)) {

				$('label[for="memberPassword"] .error_box').html("비밀번호는 영문자와 숫자를 사용하여 6~15자로 작성해 주십시오.");
				return;
			} else {
				$('label[for="memberPassword"] .error_box').html("");
			}

			/* 비밀번호 재확인 */

			/* 비밀번호 일치 여부 확인 */
			if (memberPassword != passwordCheck) {
				$('label[for="passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
				$('#passwordCheck').focus();
				return;
			}
		}// end of if(비밀번호 입력 여부)

		/* 이름 */
		if (memberName == '') {
			$('label[for="memberName"] .error_box').html(blank);
			$('#memberName').focus();
			return;
		} else {
			$('label[for="memberName"] .error_box').html("");
		}
		if (!RegexName.test(memberName)) {

			$('label[for="memberName"] .error_box').html("이름 형식이 올바르지 않습니다.");
			return;
		} else {
			$('label[for="memberName"] .error_box').html("");
		}



		/* 전화번호 */
		if (memberTel == '') {
			$('label[for="memberTel"] .error_box').html(blank);
			$('#memberTel').focus();
			return false;
		} else {
			$('label[for="memberTel"] .error_box').html("");
		}

		if (!RegexTel.test(memberTel)) {

			$('label[for="memberTel"] .error_box').html("전화번호 형식이 올바르지 않습니다. ex)010-000~0-000~0");
			return;
		} else {
			$('label[for="memberTel"] .error_box').html("");
		}


		document.memberUpdateForm.submit();
		alert("회원 정보 수정이 완료되었습니다.");
	}) //end of #btnMemberUpdate






	$('#btnMemberDelete').click(function() {
		var result = confirm("정말 탈퇴하시겠습니까?");
		if (result) {
			document.memberDelete.submit();
		}

	})



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

			location.href = "/sign/sign-up-b"

		}

	})

	$('#btn_signCancel').click(function(){
		location.href = "/sign/sign-in"
	})



	/*$('#btn_termCheck').click(function() {
		//이용약관에 체크 했는지 확인
		if (!$("#btnAgree").is(':checked')) {
			// 체크 X
			$('.check_error_box').html("<h6>필수 이용 약관에 동의해주세요.</h6>");
			$('.check_error_box').css('color', '#dc3545');
			return;
		} else {
			// 체크 O
			$('.check_error_box').html("");
			$.ajax({
				type: 'post',
				url: 'emailCheck.do',
				data: { memberEmail: $('#memberEmail').val() },
				contentType: 'application/x-www-form-urlencoded;charset=utf-8',
				success: function(result) {
					// 중복 검사 후 나오는 결과 에러박스에 출력
					if (result == 'Y') {
						$('label[for="memberEmail"] .error_box').html("");
						document.member_frm.submit();
						alert("회원가입이 되었습니다.");
					} else {
						$('label[for="memberEmail"] .error_box').css('color', '#ED7A64');
						$('label[for="memberEmail"] .error_box').html("이메일 중복 여부를 확인해주세요.");
						emailCheak = false;
						return;
					}
				},
				error: function(err) {
					alert('실패');
					console.log(err);
				}
			}); //end of ajax			
		}
	})*/








})