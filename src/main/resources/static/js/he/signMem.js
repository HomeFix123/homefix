
/*개인 회원가입 전화번호 자동 하이픈*/
$(function() {
	/**
*   유효성 검사 정규식
 */
 	var RegexmemId = /^[a-zA-z0-9]{4,12}$/; //아이디는 영문 대소문자와 숫자 4~12자리
	var RegexmemNick = /^[가-힣a-zA-Z0-9]{1,10}$/;
	var RegexmemEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var RegexmemPW =/^(?=.*[a-zA-Z])(?=.*[0-9]).{6,15}$/;
	var RegexmemName = /^[가-힣]+$/;
	/*var RegexTel = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{3,4})$/; (따로 작성했기에 이건 주석처리함)*/

	var memblank = "필수 입력 사항입니다!!";
	
	//카카오 api 우편번호 찾기
	$('#btn_postCheak').click(function() {
		
		new daum.Postcode({
			oncomplete: function(data) { //선택시 입력값 세팅
				console.log(data);
				document.getElementById("mem_firstAddress").value = data.address; // 주소 넣기
				document.getElementById("postNum").value = data.zonecode; // 주소 넣기
				document.querySelector("input[name=addr_d").focus(); //상세입력 포커싱
			}
		}).open();
	})
	
	//아이디 중복버튼 클릭이벤트
	var memidCheak = false;

	$('#btn_idCheak').click(function() {
		//alert("띵띵");
		// 아이디 중복검사 확인 여부
		$('label[for="la_memberId"] .error_box_id').html("");
		var memberId = $.trim($("#memberId").val());
		console.log(memberId);
		//alert(memberId);
		// 입력값이 없을 때 에러박스
		if (memberId == '') {
			//alert(memberId);
			$('label[for="la_memberId"] .error_box_id').html(memblank);
			$('label[for="la_memberId"] .error_box_id').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexmemId.test(memberId)) {
			$('label[for="la_memberId"] .error_box_id').html("아이디 형식이 올바르지 않습니다.");
			$('label[for="la_memberId"] .error_box_id').css('color', '#dc3545');
			return;
		}
		
		// 아이디  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/member/memIdChack',
			data: { id: $('#memberId').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				console.log(result);
				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('label[for="la_memberId"] .error_box_id').css('color', '#4ABA99');
					$('label[for="la_memberId"] .error_box_id').html("사용 가능한 아이디입니다.");
					memidCheak = true;
				} else{
					$('label[for="la_memberId"] .error_box_id').css('color', '#ED7A64');
					$('label[for="la_memberId"] .error_box_id').html("사용할 수 없는 아이디입니다.");
					memidCheak = false;
				}
			},  
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#btn_idCheak').click	
	
	//닉네임 중복버튼 클릭이벤트
	var memnickCheak = false;

	$('#nickCheak').click(function() {
		//alert("띵띵");
		// 아이디 중복검사 확인 여부
		$('.error_box_nic').html("");
		var memberNick = $.trim($("#memberNick").val());
		//alert(memberId);
		// 입력값이 없을 때 에러박스
		if (memberNick == '') {
			//alert(memberId);
			$('.error_box_nic').html(memblank);
			$('.error_box_nic').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexmemNick.test(memberNick)) {
			$('.error_box_nic').html("닉네임 형식이 올바르지 않습니다.");
			$('.error_box_nic').css('color', '#dc3545');
			return;
		}
		
		// 닉네임  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/member/memNickChack',
			data: { nickname: $('#memberNick').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {

				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('.error_box_nic').css('color', '#4ABA99');
					$('.error_box_nic').html("사용 가능한 닉네임입니다.");
					memnickCheak = true;
				} else {
					$('.error_box_nic').css('color', '#ED7A64');
					$('.error_box_nic').html("사용할 수 없는 닉네임입니다.");
					memnickCheak = false;
				}
			},
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#nickCheak').click
	
	
	//이메일 중복버튼 클릭이벤트
	var memmailCheak = false;

	$('#mem_emailCheak').click(function() {
		//alert("띵띵");
		// 이메일 중복검사 확인 여부
		$('.error_box_mail').html("");
		var memberEmail = $.trim($("#memberEmail").val());
		
		// 입력값이 없을 때 에러박스
		if (memberEmail == '') {
			//alert(memberId);
			$('.error_box_mail').html(memblank);
			$('.error_box_mail').css('color', '#dc3545');
			return false;
		}
		// 형식에 맞지 않을 때 나오는 에러박스
		if (!RegexmemEmail.test(memberEmail)) {
			$('.error_box_mail').html("이메일 형식이 올바르지 않습니다.");
			$('.error_box_mail').css('color', '#dc3545');
			
			return;
		}
		
		// 이메일  중복 검사 - DB와 비교
		$.ajax({
			type: 'get',
			url: '/sign/member/memEmail',
			data: { email: $('#memberEmail').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				//alert(result);
				// 중복 검사 후 나오는 결과 에러박스에 출력
				if (result == 'Y') {
					$('.error_box_mail').css('color', '#4ABA99');
					$('.error_box_mail').html("사용 가능한 이메일입니다.");
					memmailCheak = true;
				} else {
					$('.error_box_mail').css('color', '#ED7A64');
					$('.error_box_mail').html("사용할 수 없는 이메일입니다.");
					memmailCheak = false;
				}
			},
			error: function(err) {
				alert('실패');
				console.log(err);
			}
		}); //end of ajax
	}); // end of $('#mem_emailCheak').click
	


})
/*전화번호 자동 하이픈 및 유효성 검사*/
function autoHypenTel(str) {
  str = str.replace(/[^0-9]/g, '');
  var tmp = '';

  if (str.substring(0, 2) == 02) {
    // 서울 전화번호일 경우 10자리까지만 나타나고 그 이상의 자리수는 자동삭제
    if (str.length < 3) {
      return str;
    } else if (str.length < 6) {
      tmp += str.substr(0, 2);
      tmp += '-';
      tmp += str.substr(2);
      return tmp;
    } else if (str.length < 10) {
      tmp += str.substr(0, 2);
      tmp += '-';
      tmp += str.substr(2, 3);
      tmp += '-';
      tmp += str.substr(5);
      return tmp;
    } else {
      tmp += str.substr(0, 2);
      tmp += '-';
      tmp += str.substr(2, 4);
      tmp += '-';
      tmp += str.substr(6, 4);
      return tmp;
    }
  } else {
    // 핸드폰 및 다른 지역 전화번호 일 경우
    if (str.length < 4) {
      return str;
    } else if (str.length < 7) {
      tmp += str.substr(0, 3);
      tmp += '-';
      tmp += str.substr(3);
      return tmp;
    } else if (str.length < 11) {
      tmp += str.substr(0, 3);
      tmp += '-';
      tmp += str.substr(3, 3);
      tmp += '-';
      tmp += str.substr(6);
      return tmp;
    } else {
      tmp += str.substr(0, 3);
      tmp += '-';
      tmp += str.substr(3, 4);
      tmp += '-';
      tmp += str.substr(7);
      return tmp;
    }
  }

  return str;
}
/*/개인 회원가입 전화번호 자동 하이픈 끝*/



/* 개인 회원가입 */
$('#btn_mem_signUp').click(function(){
	
	//input에 입력된 값을 변수에 담기 (trim은 공백제거)
	let mem_id =$.trim($("#memberId").val());
	let mem_name =$.trim($("#memName").val());
	let mem_nick =$.trim($("#memberNick").val());
	let mem_pass =$.trim($("#memberPassword").val());
	let mem_passCheak =$.trim($("#passwordCheck").val());
	let mem_email =$.trim($("#memberEmail").val());
	let mem_tel =$.trim($("#telInput").val());
	let mem_zip =$.trim($("#postNum").val());
	let mem_addr =$.trim($("#mem_firstAddress").val());
	let mem_addrd =$.trim($("#mem_secondAddress").val());
	
	/*아이디*/
	if(mem_id == ''){
		$('.error_box_id').html(memblank);
		$('.error_box_id').css('color', '#dc3545');
		$('#memberId').focus();
		return false;
	}else{
		$('.error_box_id').html("");
	}
	
	if(!RegexmemId.test(mem_id)){
		$('.error_box_id').html("아이디 형식이 올바르지 않습니다.");
		$('.error_box_id').css('color', '#dc3545');
		$('#memberId').focus();
		return false;
	}else{
		$('.error_box_id').html("");
	}
	/*/아이디 끝*/
	
	/*이름*/
	if(mem_name == ''){
		$('.error_box_name').html(memblank);
		$('#memName').focus();
		return false;
	}else{
		$('.error_box_name').html("");
	}
	
	if(!RegexmemName.test(mem_name)){
		$('.error_box_name').html("이름 형식이 올바르지 않습니다.");
		return false;
	}else{
		$('.error_box_name').html("");
	}
	/*/이름 끝*/
	
	/* 비밀번호 */
		if (memberPassword == '') {
			$('.error_box_pass').html(blank);
			$('.error_box_pass').css('color', '#dc3545');
			$('#memberPassword').focus();
			return false;
		} else {
			$('.error_box_pass').html("");
		}

		if (!RegexPW.test(memberPassword)) {

			$('.error_box_pass').html("8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하여 만들어 주십시오.");
			$('.error_box_pass').css('color', '#dc3545');
			$('#memberPassword').focus();
			return false;
		} else {
			$('.error_box_pass').html("");
		}

		/* 비밀번호 재확인 */
		if (passwordCheck == '') {
			$('.error_box_passcheak').html("필수 입력 사항입니다.");
			$('.error_box_passcheak').css('color', '#dc3545');
			$('#passwordCheck').focus();
			return false;
		} else {
			$('.error_box_passcheak').html("");
		}


		/* 비밀번호 일치 여부 확인 */
		if (memberPassword != passwordCheck) {
			$('.error_box_passcheak').html("비밀번호가 일치하지 않습니다.");
			$('.error_box_passcheak').css('color', '#dc3545');
			$('#passwordCheck').focus();
			return false;
		}
	/* /비밀번호 끝*/
	
});
/* /개인 회원가입 */

/*이용약관 체크박스*/
// 체크박스 전체 선택
// 체크박스 전체 선택
$(".checkbox_group").on("click", "#check_all", function () {
    $(this).parents(".checkbox_group").find('input').prop("checked", $(this).is(":checked"));
});

// 체크박스 개별 선택
$(".checkbox_group").on("click", ".normal", function() {
    var is_checked = true;

    $(".checkbox_group .normal").each(function(){
        is_checked = is_checked && $(this).is(":checked");
    });

    $("#check_all").prop("checked", is_checked);
});
