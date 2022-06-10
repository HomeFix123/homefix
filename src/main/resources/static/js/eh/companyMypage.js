//companyprofile 회원정보수정-----------------------------------------
$(function() {
	$('#postBtn').click(function() {
		$('#sample6_postcode').val('')
		$('#sample6_address').val('')
		/*카카오 주소 API*/
		new daum.Postcode(
			{
				oncomplete: function(data) {
					document.getElementById('sample6_postcode').value = data.zonecode;
					document.getElementById("sample6_address").value = data.address;

					// 커서를 상세주소 필드로 이동한다.
					document.getElementById("sample6_detailAddress")
						.focus();
				}
			}).open();
	})

	// 에러박스 문구
	var blank = "  필수 입력 사항입니다.";
	var RegexId = /^[a-zA-z0-9]{4,12}$/; //아이디는 영문 대소문자와 숫자 4~12자리
	//업체명
	var RegexCompany = /^[가-힣a-zA-Z0-9]{1,10}$/;
	var RegexEmail = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var RegexPW = /^(?=.*[a-zA-z])(?=.*[0-9]).{4,10}$/; //4 ~ 10자 영문, 숫자 최소 한가지씩 조합
	var RegexName = /^[가-힣]+$/;
	//전화번호
	var RegexTel = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
	var RegexCompanyNum = /^(\d{3,3})+[-]+(\d{2,2})+[-]+(\d{5,5})$/;

	//이메일 중복 버튼 클릭 이벤트
	var emailCheak = false;
	$('#bs_btn_emailCheak').click(function() {
		// 이메일 중복검사 확인 여부
		$('label[for="bs_memberEmail"] .error_box').html("");
		var memberEmail = $.trim($('#bs_memberEmail').val());

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
			data: { email: $('#bs_memberEmail').val(), email2: $('#bs_memberEmailHidden').val() },
			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
			success: function(result) {
				// 중복 검사 후 나오는 결과 에러박스에 출력
				console.log(result)
				if (result == 'Y') {
					$('label[for="bs_memberEmail"] .error_box').css('color', '#4ABA99');
					$('label[for="bs_memberEmail"] .error_box').html("사용 가능한 이메일입니다.");
					emailCheak = true;
				} else if (result == 'S') {
					$('label[for="bs_memberEmail"] .error_box').css('color', '#ED7A64');
					$('label[for="bs_memberEmail"] .error_box').html("  같은 이메일입니다.");
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


	//이미지 변경
	const form = $('#companyModify');
	const img = $('#cinfo_img');

	// uuid 생성
	function uuid(file_nm) {
		const s4 = () => ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1)
		return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + file_nm.substr(file_nm.indexOf("."), file_nm.length - 1).toLowerCase();
	}

	// 이미지 저장 ajax
	function saveImage(formData) {
		let answer = false;
		$.ajax({
			type: "POST",
			url: "http://140.238.11.118:5000/upload",
			processData: false,
			contentType: false,
			data: formData,
			async: false
		}).done(() => {
			answer = true;
		}).fail(() => {
		})
		return answer;
	}



	//섬네일 이미지 미리보기
	function readImage(input) {
		if (input.files && input.files[0]) {
			const reader = new FileReader();
			reader.onload = (e) => {
				const previewImage = document.getElementById('previewImage');
				previewImage.src = e.target.result;
			}
			reader.readAsDataURL(input.files[0]);
		}
	}

	// 이벤트 리스너
	document.getElementById('inputImage').addEventListener('change', (e) => {
		readImage(e.target);
	})



	$('#companyUpdate').click(function() {

		// input에 입력된 값을 공백제거하고 변수에 담기
		var memberNickname = $.trim($("#nicname").val());
		var memberPassword = $.trim($("#password").val());
		var passwordCheck = $.trim($("#two_password").val());
		var memberTel = $.trim($("#first-name1").val());
		var memberEmail = $.trim($("#bs_memberEmail").val());
		var zip = $.trim($("#sample6_postcode").val());
		var address = $.trim($("#sample6_address").val());
		var detailaddress = $.trim($("#sample6_detailAddress").val());


		/* 업체명 */
		if (memberNickname == '') {
			$('label[for="nicname"] .error_box').html(blank);
			$('label[for="nicname"] .error_box').css('color', '#dc3545');
			$('#nicname').focus();
			return false;
		} else {
			$('label[for="nicname"] .error_box').html("");
		}

		if (!RegexCompany.test(memberNickname)) {
			$('label[for="nicname"] .error_box').html("닉네임 형식이 올바르지 않습니다.");
			$('label[for="nicname"] .error_box').css('color', '#dc3545');
			$('#nicname').focus();
			return false;
		} else {
			$('label[for="nicname"] .error_box').html("");
		}


		/* 비밀번호 */
		if (memberPassword == '') {
			$('label[for="password"] .error_box').html(blank);
			$('label[for="password"] .error_box').css('color', '#dc3545');
			$('#password').focus();
			return false;
		} else {
			$('label[for="password"] .error_box').html("");
		}

		if (!RegexPW.test(memberPassword)) {
			$('label[for="password"] .error_box').html("4 ~ 10자 영문, 숫자를 최소 한가지씩 조합하여 만들어 주십시오.");
			$('label[for="password"] .error_box').css('color', '#dc3545');
			$('#password').focus();
			return false;
		} else {
			$('label[for="password"] .error_box').html("");
		}

		/* 비밀번호 재확인 */
		if (passwordCheck == '') {
			$('label[for="two_password"] .error_box').html("필수 입력 사항입니다.");
			$('label[for="two_password"] .error_box').css('color', '#dc3545');
			$('#two_password').focus();
			return false;
		} else {
			$('label[for="two_password"] .error_box').html("");
		}

		/* 비밀번호 일치 여부 확인 */
		if (memberPassword != passwordCheck) {
			$('label[for="two_password"] .error_box').html("비밀번호가 일치하지 않습니다.");
			$('label[for="two_password"] .error_box').css('color', '#dc3545');
			$('#two_password').focus();
			return false;
		}




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
			$('label[for="first-name1"] .error_box').html(blank);
			$('#first-name1').focus();
			return false;
		} else {
			$('label[for="first-name1"] .error_box').html("");
		}
		if (!RegexTel.test(memberTel)) {

			$('label[for="first-name1"] .error_box').html("전화번호 형식이 올바르지 않습니다. ex)010-000~0-000~0");
			return false;
		} else {
			$('label[for="first-name1"] .error_box').html("");
		}


		// form submit버튼 누른 후 동작
		// 이미지 저장후 submit 
		const imageInput = $('#inputImage')[0]; // input에서 이미지 가져오기
		const formData = new FormData();
		if (imageInput.files[0] != null) {
			// 이미지명 uuid로 변경
			const imgName = uuid(imageInput.files[0].name);
			formData.append("file", imageInput.files[0], imgName);

			const result = saveImage(formData); //이미지 저장
			if (result) {
				// 결과가 성공시 input hidden에 이미지명 넣기(DB에 저장할 이미지명)
				img.val(imgName);
			}
		}
		form.submit()
		alert("회원 정보 수정이 완료되었습니다.");
	}) //end of #btnMemberUpdate



	//회원탈퇴------------------------------------------------------------------------

	$('#Withdrawal').click(function() {
		var result = confirm("정말 탈퇴하시겠습니까?");
		if (result) {
			const id = $('#companyId').text();
			const pass = $('#password').val();
			if (pass != "") {
				$.ajax({
					url: "/sign/Withdrawal",
					data: { pass: $('#password').val() },
					contentType: 'application/x-www-form-urlencoded;charset=utf-8', //스트링 타입으로 보내기 포스트형식으로 보내면 안됨
					type: 'delete',//전송 타입을 Delete로 중요!
					success: function(result) {
						console.log(result);
						if (result == 'Y') {
							console.log(result)
							alert("탈퇴에 성공하였습니다.")
							location.href = "/sign/sign-in";
						} else {
							alert("비밀번호가 틀립니다.")
						}
					},
					error: function(err) {
						console.log(err);
					}
				});
			} else {
				alert("비밀번호를 입력하세요.");
			}
		}
	})



	//결제내역 더보기----------------------------------------------------------------
	/*더보기로 페이징 처리
	1. 처음 페이지 접근 시 1페이지 정보를 가져온다
	2. 버튼을 누르면 ajax 2페이지 정보를 가져온다
	3. 2페이지 정보를 1페이지 정보 밑에 추가한다*/

	var pageA = 1;  // 초기 페이지
	$('#moreBtn').click(function() {
		pageA += 1;
		$.ajax({
			data: { "page": pageA },
			type: "GET",
			url: "/company/myPaymentInfo",
			async: false,
			contentType: 'application/x-www-form-urlencoded;charset=utf-8'
		}).done((Result) => {
			$.each(Result, function(i, payment) { //Result 리스트에 들어있는 payment객체로 each문 돌림.
				let AddContent = '<tr><td>' + payment.pid + '</td><td>' + payment.method + '</td><td >' + payment.amount + '</td><td>' + payment.pday
					+ '</td><td >' + payment.plast + '</td></tr >';
				$('#paymentInfoAdd').append(AddContent);
			})

			if (Result.length < 6) {  //리스트 길이가 6보다 작으면 버튼 삭제
				$('#moreBtn').remove();
			}

		}).fail((err) => {
			// 실패했을 때
			console.log(err);
		});

	})




	//내가가 쓴 글(업체후기) 더보기 -------------------------------------------------------------------------*/
	var pageB = 1;
	$('#moreBtnReview').click(function() {
		pageB += 1;
		$.ajax({
			data: { "page": pageB },
			type: "GET",
			url: "/company/myRiew",
			async: false,
			contentType: 'application/x-www-form-urlencoded;charset=utf-8'
		}).done((Result) => {
			$.each(Result, function(i, review) { //Result 리스트에 들어있는 review객체로 each문 돌림.
				let AddContent = '<div  class="col-12 col-md-6 col-lg-4  ">' + '<div class="sa-post">' + '<div class="entry-header">' + '<div class="entry-thumbnail">' + '<a href="/review">'
					+ '<img src="http://140.238.11.118:5000/upload/' + review.rimgadr + '"alt="Image" class="img-fluid" id="thumbnailImage">'
					+ '</a>' + '</div>' + '</div>' + '<div class="course-info">' + '<div class="info">' + '<h2 class="title text-center">'
					+ '<a >' + review.rtitle + '</a>' + '</h2>' + '<p class="text-right">' + review.company.name + '</p>'
					+ '</div >' + '<div class="sa-meta">' + '<ul class="global-list">' + '<li>' + '<a href="#">' + ' <span>' + '<i class="fas fa-heart">' + '</i>'
					+ '</span>' + '</a>' + '</li>' + '<li>' + '<a href="#">' + ' <i class="far fa-eye">' + '</i>' + '<span>' + review.rcnt + '</span>'
					+ '</a>' + '</li>' + '</ul>' + '</div >' + '</div >' + '</div >' + '</div >';
				$('#myReviswList').append(AddContent);
			})

			if (Result.length < 6) {  //리스트 길이가 6보다 작으면 버튼 삭제
				$('#moreBtnReview').remove();
			}

		}).fail((err) => {
			// 실패했을 때
			console.log(err);
		});
	})

	//신청한견적 더보기------------------------------------------------------------------- 
	var pageC = 1;
	$('#moreBtnEstimation').click(function() {
		pageC += 1;
		$.ajax({
			data: { "page": pageC },
			type: "GET",
			url: "/company/registEst",
			async: false,
			contentType: 'application/x-www-form-urlencoded;charset=utf-8'
		}).done((Result) => {
			$.each(Result, function(i, estimation) { //Result 리스트에 들어있는 estimation객체로 each문 돌림.
				let AddContent = '<tr > <td> <a class="estNum" href="/estimation/ChosenDetail(id=' + estimation.eid + '"> <span>' + estimation.eid +
					'</span> </a> </td> <td> <span>' + estimation.eaddr + '</span> </td> <td> <span>' + estimation.startDay +
					'</span> </td> <td> <span>' + estimation.member.id + '</span></td></tr>';

				$('#registEstTbody').append(AddContent);
			})

			if (Result.length < 6) {  //리스트 길이가 6보다 작으면 버튼 삭제
				$('#moreBtnEstimation').remove();
			}

		}).fail((err) => {
			// 실패했을 때
			console.log(err);
		});
	})


})