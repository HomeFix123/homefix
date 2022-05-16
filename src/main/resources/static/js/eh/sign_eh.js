$(function(){
   

/*유효성 검사 정규식*/

var RegexId=/^[a-z]+[a-z0-9]{5,19}$/g; //영문자로 시작하는 영문자 또는 숫자 6~20자 
var RegexNick = /^[가-힣a-zA-Z0-9]{1,10}$/;
var RegexEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
var RegexPW =/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/; //8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합
var RegexName = /^[가-힣]+$/;
var RegexTel = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{3,4})$/;


// 에러박스 문구
var blank = "  필수 입력 사항입니다.";

/*********[ 회원가입 페이지 ]*************************************************************/



//카카오 api 우편번호 찾기
$('#btn_postCheak').click(function(){
	new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
            	console.log(data);
                document.getElementById("firstAddress").value = data.address; // 주소 넣기
                document.getElementById("postNum").value = data.zonecode; // 주소 넣기
                document.querySelector("input[name=secondAddress").focus(); //상세입력 포커싱
            }
        }).open();
})	
	
	
	
	
//아이디 중복 클릭이벤트
var idCheak = false;

$('#btn_idCheak').click(function(){
	
	// 아이디 중복검사 확인 여부
	$('label[for="memberId"] .error_box').html("");
	var memberId = $.trim($("#memberId").val());
	
	// 입력값이 없을 때 에러박스
	if(memberId == ''){
		$('label[for="memberId"] .error_box').html(blank);
		$('label[for="memberId"] .error_box').css('color','#dc3545');
		return false;
	}
	// 형식에 맞지 않을 때 나오는 에러박스
	if( !RegexId.test(memberId) ){
		$('label[for="memberId"] .error_box').html("아이디 형식이 올바르지 않습니다.");
		$('label[for="memberId"] .error_box').css('color','#dc3545');
		return;
	}
	
	// 아이디  중복 검사 - DB와 비교
 	  $.ajax({
    	type : 'post',
    	url : 'idCheck.do',
    	data : { MemberId : $('#memberId').val() },
    	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
    	success : function(result){
    		
    		// 중복 검사 후 나오는 결과 에러박스에 출력
    		if(result == 'Y'){
	        		$('label[for="memberId"] .error_box').css('color','#4ABA99');
	        		$('label[for="memberId"] .error_box').html("사용 가능한 아이디입니다.");
	        		idCheak = true;
				}else{
	        		$('label[for="memberId"] .error_box').css('color','#ED7A64');
	        		$('label[for="memberId"] .error_box').html("사용할 수 없는 아이디입니다.");
	        		idCheak = false;
				}
    	},
    	error : function(err){
			alert('실패');
    		console.log(err);
    	}
    }); //end of ajax
}); // end of $('#btn_idCheak').click	
	
	
	
	
	//이메일 중복 버튼 클릭 이벤트
var emailCheak = false;
$('#btn_emailCheak').click(function(){
	// 이메일 중복검사 확인 여부
	$('label[for="memberEmail"] .error_box').html("");
	var memberEmail = $.trim($("#memberEmail").val());
	
	// 입력값이 없을 때 에러박스
	if(memberEmail == ''){
		$('label[for="memberEmail"] .error_box').html(blank);
		$('label[for="memberEmail"] .error_box').css('color','#dc3545');
		return false;
	}
	
	// 형식에 맞지 않을 때 나오는 에러박스
	if( !RegexEmail.test(memberEmail) ){
		$('label[for="memberEmail"] .error_box').css('color','#dc3545');
		$('label[for="memberEmail"] .error_box').html("이메일 형식이 올바르지 않습니다.");
		return;
	}
	
	// 이메일 중복 검사 - DB와 비교
 	  $.ajax({
    	type : 'post',
    	url : 'emailCheck.do',
    	data : { MemberEmail : $('#memberEmail').val() },
    	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
    	success : function(result){
    		// 중복 검사 후 나오는 결과 에러박스에 출력
    		if(result == 'Y'){
	        		$('label[for="memberEmail"] .error_box').css('color','#4ABA99');
	        		$('label[for="memberEmail"] .error_box').html("사용 가능한 이메일입니다.");
	        		emailCheak = true;
				}else{
	        		$('label[for="memberEmail"] .error_box').css('color','#ED7A64');
	        		$('label[for="memberEmail"] .error_box').html("사용할 수 없는 이메일입니다.");
	        		emailCheak = false;
				}
    	},
    	error : function(err){
			alert('실패');
    		console.log(err);
    	}
    }); //end of ajax
}); // end of $('#btn_emailCheak').click



	/*회원가입 버튼 클릭 -> 필수 입력 사항 + 유효성 검사*/
$('#btn_signUp').click(function(){

	// input에 입력된 값을 공백제거하고 변수에 담기
	var memberNickname = $.trim($("#memberNickname").val());
	var memberEmail = $.trim($("#memberEmail").val());
	var memberPassword = $.trim($("#memberPassword").val());
	var passwordCheck = $.trim($("#passwordCheck").val());
	var memberName = $.trim($("#memberName").val());
	var memberBirth = $.trim($("#memberBirth").val());
	var memberTel = $.trim($("#memberTel").val());
	var postNum = $.trim($("#postNum").val());
		
	/* 이름 */
	if(memberName == ''){
		$('label[for="memberName"] .error_box').html(blank);
		$('label[for="memberName"] .error_box').css('color','#dc3545');
		$('#memberName').focus();
    		return;
		}else{
		$('label[for="memberName"] .error_box').html("");
		}
	if( !RegexName.test(memberName) ){

		$('label[for="memberName"] .error_box').html("한글만 입력 가능합니다.");
		$('label[for="memberName"] .error_box').css('color','#dc3545');
		return;
	}else{
		$('label[for="memberName"] .error_box').html("");
		}
		
	/* 닉네임 */
	if(memberNickname == ''){
		$('label[for="memberNickname"] .error_box').html(blank);
		$('label[for="memberNickname"] .error_box').css('color','#dc3545');
		$('#memberNickname').focus();
    		return;
		}else{
		$('label[for="memberNickname"] .error_box').html("");
		$('label[for="memberNickname"] .error_box').css('color','#dc3545');
		}		
	
	if( !RegexNick.test(memberNickname) ){

		$('label[for="memberNickname"] .error_box').html("한글, 영문 그리고 숫자만 입력 가능합니다.");
		return;
	}else{
		$('label[for="memberNickname"] .error_box').html("");
		}
	
	
	
	
	/* 비밀번호 */
	if(memberPassword == ''){
		$('label[for="memberPassword"] .error_box').html(blank);
		$('label[for="passwordCheck"] .error_box').css('color','#dc3545');
		$('#memberPassword').focus();
    		return;
		}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	if( !RegexPW.test(memberPassword) ){

		$('label[for="memberPassword"] .error_box').html("8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합하여 만들어 주십시오.");
		$('label[for="memberPassword"] .error_box').css('color','#dc3545');
		return;
	}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	/* 비밀번호 재확인 */
	if(passwordCheck == ''){
		$('label[for="passwordCheck"] .error_box').html("필수 입력 사항입니다.");
		$('label[for="passwordCheck"] .error_box').css('color','#dc3545');
		$('#passwordCheck').focus();
    		return;
		}else{
		$('label[for="passwordCheck"] .error_box').html("");
		}
	
	
	/* 비밀번호 일치 여부 확인 */
	if(memberPassword != passwordCheck){
		$('label[for="passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
		$('#passwordCheck').focus();
		return;
	}

	if(postNum == ''){
		$('label[for="postNum"] .error_box').html(blank);
		$('label[for="postNum"] .error_box').css('color','#dc3545');
		}
		



		
	/* 생년월일 */
	if(memberBirth == ''){
		$('label[for="memberBirth"] .error_box').html(blank);
		$('#memberBirth').focus();
    		return;
		}else{
		$('label[for="memberBirth"] .error_box').html("");
		}
	/* 이메일 */
	if(memberEmail == ''){
		$('label[for="memberEmail"] .error_box').html(blank);
		$('#memberEmail').focus();
    		return;
		}else{
		$('label[for="memberEmail"] .error_box').html("");
		}	
	
	if( !RegexEmail.test(memberEmail) ){

		$('label[for="memberEmail"] .error_box').html("이메일 형식이 올바르지 않습니다.");
		return;
	}else{
		$('label[for="memberEmail"] .error_box').html("");
		}
		
	
	/* 휴대전화 */
	if(memberTel == ''){
		$('label[for="memberTel"] .error_box').html(blank);
		$('#memberTel').focus();
    		return false;
		}else{
		$('label[for="memberTel"] .error_box').html("");
		}
	if( !RegexTel.test(memberTel) ){

		$('label[for="memberTel"] .error_box').html("전화번호 형식이 올바르지 않습니다. ex)010-000~0-000~0");
		return;
	}else{
		$('label[for="memberTel"] .error_box').html("");
		}
	
	
	
});


/*********************************************************************
	[ 로그인 페이지 ]
	로그인 버튼 클릭
*/

$('#btnLogin').click(function(){

	var memberEmail = $.trim($("#memberEmail").val());
	var memberPassword = $.trim($("#memberPassword").val());
	var rememberEmail = false;

	/* 이메일 */
	if(memberEmail == ''){
		$('label[for="memberEmail"] .error_box').html(blank);
		$('#memberEmail').focus();
    		return;
		}else{
		$('label[for="memberEmail"] .error_box').html("");
		}	
	
	/* 비밀번호 */
	if(memberPassword == ''){
		$('label[for="memberPassword"] .error_box').html(blank);
		$('#memberPassword').focus();
    		return;
		}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	/* 이메일 기억하기 체크 박스*/
	if( $("#rememberEmail").is(':checked') ){
		rememberEmail = true;
		}//end of if
	
  $.ajax({
	type : 'post',
	url : 'loginCheck.do',
	data : { memberEmail : $("#memberEmail").val(),
			memberPassword : $("#memberPassword").val(),
			rememberEmail : rememberEmail
	 		},
	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
	success : function(result){
		// 중복 검사 후 나오는 결과 에러박스에 출력
		if(result == "N"){
        		$('.error_box.login').html("존재하는 회원이 아니거나 비밀번호가 일치하지 않습니다.");
			
			}else{
			// 결과가 result = "Y"이면 로그인 성공 -> loginMove.do로 이동
        		document.loginForm.submit();
			}
	},
	error : function(err){
		alert(err);
		console.log(err);
	}
	});//end of ajax
	
	// 이메일 중복 여부 체크 했는지 확인
	if( !emailCheak ){
		$('label[for="memberEmail"] .error_box').html("이메일 중복 여부를 확인해주세요.");
		return;
	}else {
		$('label[for="memberEmail"] .error_box').html("");
	}
	
}); //end of #btnLogin 

/*********** ********************************************************/



/*********************************************************************
	[ 비밀번호 재설정 페이지(1) ]
	비밀번호 찾기 버튼 클릭
*/
let ramdom;
$('#btn_emailSend').click(function(){
	
	
	
	// 인증번호 전송
 	  $.ajax({
    	type : 'post',
    	url : 'emailSend.do',
    	data : { memberEmail : $('#memberEmail').val(),
    	 		},
    	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
    	success : function(result){
    		ramdom = result;
    	},
    	error : function(err){
	        
			alert('실패');
    		console.log(err);
    	}
    }); //end of ajax

})


$('#btnPwSearch').click(function(){
	
	// input에 입력된 값을 공백제거하고 변수에 담기
	var authenticationNumber = $.trim($("#authenticationNumber").val());
	
	// 인증번호와 입력한 문자열이 같을 때 폼 전송
	if(ramdom === authenticationNumber){
		document.pwSearchForm.submit();
	}else{
		$('.error_box.pwSearch').html("인증번호가 일치하지 않습니다.");
	}
	
}) // end of #btnPwSearch



// [ 비밀번호 재설정 페이지(2) ]
$('#btnPwChange').click(function(){
	var memberPassword = $.trim($("#memberPassword").val());
	var passwordCheck = $.trim($("#passwordCheck").val());
	
	/* 비밀번호 */
	if(memberPassword == ''){
		$('label[for="memberPassword"] .error_box').html(blank);
		$('#memberPassword').focus();
    		return;
		}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	if( !RegexPW.test(memberPassword) ){

		$('label[for="memberPassword"] .error_box').html("비밀번호는 영문자와 숫자를 사용하여 6~15자로 작성해 주십시오.");
		return;
	}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	/* 비밀번호 재확인 */
	if(passwordCheck == ''){
		$('label[for="passwordCheck"] .error_box').html("필수 입력 사항입니다.");
		$('#passwordCheck').focus();
    		return;
		}else{
		$('label[for="passwordCheck"] .error_box').html("");
		}
	
	
	/* 비밀번호 일치 여부 확인 */
	if(memberPassword != passwordCheck){
		$('label[for="passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
		$('#passwordCheck').focus();
		return;
	}
	document.pwChangeForm.submit();
	alert("비밀번호가 변경되었습니다.");
	
}); // end of #btnPwChange


$('#btnMemberUpdate').click(function(){
	
	// input에 입력된 값을 공백제거하고 변수에 담기
	var memberNickname = $.trim($("#memberNickname").val());
	var memberPassword = $.trim($("#memberPassword").val());
	var passwordCheck = $.trim($("#passwordCheck").val());
	var memberName = $.trim($("#memberName").val());
	var memberBirth = $.trim($("#memberBirth").val());
	var memberTel = $.trim($("#memberTel").val());
	
	
	/* 닉네임 */
	if(memberNickname == ''){
		$('label[for="memberNickname"] .error_box').html(blank);
		$('#memberNickname').focus();
    		return;
		}else{
		$('label[for="memberNickname"] .error_box').html("");
		}		
	
	if( !RegexNick.test(memberNickname) ){

		$('label[for="memberNickname"] .error_box').html("닉네임 형식이 올바르지 않습니다.");
		return;
	}else{
		$('label[for="memberNickname"] .error_box').html("");
		}
	
	if(memberPassword != ""){
	
	/* 비밀번호 */

	if( !RegexPW.test(memberPassword) ){

		$('label[for="memberPassword"] .error_box').html("비밀번호는 영문자와 숫자를 사용하여 6~15자로 작성해 주십시오.");
		return;
	}else{
		$('label[for="memberPassword"] .error_box').html("");
		}
	
	/* 비밀번호 재확인 */

	/* 비밀번호 일치 여부 확인 */
	if(memberPassword != passwordCheck){
		$('label[for="passwordCheck"] .error_box').html("비밀번호가 일치하지 않습니다.");
		$('#passwordCheck').focus();
		return;
	}
	}// end of if(비밀번호 입력 여부)
	
	/* 이름 */
	if(memberName == ''){
		$('label[for="memberName"] .error_box').html(blank);
		$('#memberName').focus();
    		return;
		}else{
		$('label[for="memberName"] .error_box').html("");
		}
	if( !RegexName.test(memberName) ){

		$('label[for="memberName"] .error_box').html("이름 형식이 올바르지 않습니다.");
		return;
	}else{
		$('label[for="memberName"] .error_box').html("");
		}
		
	// 생년월일 max를 오늘 날짜로 지정하기
 	var today = new Date();
 	var day = today.getDate();
 	var monty = today.getMonth()+1;
 	var year = today.getFullYear();

	if(day<10){ day = '0'+ day}
	if(monty<10){ monty = '0' + monty}
	today = year + "-" + monty + "-" + day;
	document.getElementById('memberBirth').setAttribute("max", today);
	
	
	/* 생년월일 */
	if(memberBirth == ''){
		$('label[for="memberBirth"] .error_box').html(blank);
		$('#memberBirth').focus();
    		return;
		}else{
		$('label[for="memberBirth"] .error_box').html("");
		}
	
		
	/* 휴대전화 */
	if(memberTel == ''){
		$('label[for="memberTel"] .error_box').html(blank);
		$('#memberTel').focus();
    		return false;
		}else{
		$('label[for="memberTel"] .error_box').html("");
		}
		
	if( !RegexTel.test(memberTel) ){

		$('label[for="memberTel"] .error_box').html("전화번호 형식이 올바르지 않습니다. ex)010-000~0-000~0");
		return;
	}else{
		$('label[for="memberTel"] .error_box').html("");
		}
	
	
	 document.memberUpdateForm.submit();
	 alert("회원 정보 수정이 완료되었습니다.");
}) //end of #btnMemberUpdate






$('#btnMemberDelete').click(function(){
	var result = confirm("정말 탈퇴하시겠습니까?");
	if(result){
		document.memberDelete.submit();
	}

})


// 생년월일 max를 오늘 날짜로 지정하기
 	var date = new Date();
 	var day = date.getDate();
 	var month = date.getMonth()+1;
 	var year = date.getFullYear();

	if(day<10){ day = '0'+ day}
	if(month<10){ month = '0' + month}
	var today = year + "-" + month + "-" + day;
	//document.getElementById('memberBirth').setAttribute("max", today);   
   
   
/************필수 동의 사항******************************************** */   

$('#btnAgree').click(function(){
	$('#agreeForm').toggle();
});

$('#btnChoiceAgree').click(function(){
	$('#agreeChoiceForm').toggle();
});




$("#allCheck").click(function(){
if( $("input:checkbox[id='allCheck']").is(":checked")==true){

	$('#termsService').prop("checked", true);
	$('#choicCheck').prop("checked", true);
	
}else{
  $('#termsService').prop("checked", false);
	$('#choicCheck').prop("checked", false);
}
})

$('#termsService').click(function(){
if( $('#termsService').is(":checked")==false | $('#choicCheck').is(":checked") ==false ){
$('#allCheck').prop("checked", false);
}else{
	$('#allCheck').prop("checked", true);

}})

$('#choicCheck').click(function(){
if( $('#termsService').is(":checked")==false | $('#choicCheck').is(":checked") ==false ){
$('#allCheck').prop("checked", false);
}else{
	$('#allCheck').prop("checked", true);

}})



	$('#btn_termCheck').click(function(){
	//이용약관에 체크 했는지 확인
	if( !$("#termsService").is(':checked')){
		// 체크 X
		$('.check_error_box').html("<h3>필수 이용 약관에 동의해주세요.</h3>");
		$('.check_error_box').css('color','#dc3545');
		return;
	}else{
		// 체크 O
		$('.check_error_box').html("");
		$.ajax({
			    	type : 'post',
			    	url : 'emailCheck.do',
			    	data : { memberEmail : $('#memberEmail').val() },
			    	contentType : 'application/x-www-form-urlencoded;charset=utf-8',
			    	success : function(result){
			    		// 중복 검사 후 나오는 결과 에러박스에 출력
			    		if(result == 'Y'){
			    			$('label[for="memberEmail"] .error_box').html("");
				        		document.member_frm.submit();
				        		alert("회원가입이 되었습니다.");
							}else{
				        		$('label[for="memberEmail"] .error_box').css('color','#ED7A64');
								$('label[for="memberEmail"] .error_box').html("이메일 중복 여부를 확인해주세요.");
				        		emailCheak = false;
				        		return;
							}
			    	},
			    	error : function(err){
						alert('실패');
			    		console.log(err);
			    	}
			    }); //end of ajax			
	}
	})   
   
   
   
   
 })
        
        