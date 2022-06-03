$(function() {

	/*********************payment.html************************/


	/*1.개월수 금액 변경하기*/
	$('#moneySel').click(function() {


		$('#price').text($(this).val() * '9900');
	})


	/*2.아임포트 결제API*/
	$('.pull-right').click(function() { //결제 버튼 눌렀을 떄

		var cid = $('#companyId').val();//사용자 로그인 체크
		var payCheck = new Date($('#endDay').val()); //사용자 결제 기간 남았는지 체크
		var today = new Date(); //오늘 날짜 시간

		if (payCheck > today) {//마지막 사용일 결제일 비교
			today.setFullYear(payCheck.getFullYear());
			today.setMonth(payCheck.getMonth());
			today.setDate(payCheck.getDate() + 1);
			var StartDay = today.toLocaleDateString(); //년.월.일 형식으로 가져오기(시간은 제외)
			var EndDay = new Date(StartDay);
		} else {
			var StartDay = today.toLocaleDateString(); //년.월.일 형식으로 가져오기(시간은 제외)
			var EndDay = new Date();
		}

		var selectMonth = $('#moneySel').val(); //선택한 개월수
		if (selectMonth == '1') {
			EndDay.setMonth(EndDay.getMonth() + 1);

		} else if (selectMonth == '2') {
			EndDay.setMonth(EndDay.getMonth() + 2);

		} else {
			EndDay.setMonth(EndDay.getMonth() + 3);

		}
		EndDay = EndDay.toLocaleDateString();








		if (cid != "") {

			// IMP.request_pay(param, callback) 호출
			IMP.init('imp58368556'); //자신의가맹점_키
			var price = 100 //$('#price').text();//결제 금액
			IMP.request_pay({//결제창 호출  // param
				pg: 'inicis', // version 1.1.0부터 지원.  결제를 위한 PG사를 선택
				merchant_uid: 'merchant_' + new Date().getTime(), //가맹점에서 구별할 수 있는 고유한id
				name: '주문명:HomeFix 유료회원 결제',
				buyer_name: cid, // 구매자 이름
				amount: price, //판매 가격


			}, function(rsp) { // callback
				if (rsp.success) { // 결제 성공 시 로직,
					console.log(rsp)

					const pid = rsp.imp_uid;
					const pday = StartDay;
					const plast = EndDay;
					const method = rsp.pay_method;
					const amount = rsp.paid_amount;
					const company = rsp.buyer_name;

					console.log(pid, pday, plast, method, amount, company);
					$.ajax({
						type: 'post',
						url: '/payment/paymentInfoInsert',
						data: { pid: pid, pday: pday, plast: plast, method: method, amount: amount, company: company },
						contentType: 'application/x-www-form-urlencoded;charset=utf-8',
						success: function(result) {
							console.log(result);
							location.href = "/payment/congrats";
						},
						error: function(err) {
							console.log(err);
						}
					});
				} else {    // 결제 실패 시 로직,
					var msg = '결제에 실패하였습니다.';
					msg += '에러내용 : ' + rsp.error_msg;
				}
			})
			
		} else {
			location.href = "/sign/"; //로그인 안했을 시 로그인창으로 이동
		}
	});







})