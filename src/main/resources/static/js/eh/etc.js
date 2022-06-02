$(function() {

	/*********************payment.html************************/


	/*1.개월수 금액 변경하기*/
	$('#moneySel').click(function() {


		$('#price').text($(this).val() * '9900');
	})


	/*2.아임포트 결제API*/
	$('.pull-right').click(function() { //결제 버튼 눌렀을 떄
		var payCheck = false; //사용자 결제 기간 남았는지 체크

		var companyId = $('#companyId').val();//사용자 로그인 체크
		var today = new Date(); //오늘 날짜 시간
		var StartDay = today.toLocaleDateString(); //년.월.일 형식으로 가져오기(시간은 제외)
		var selectMonth = $('#moneySel').val(); //선택한 개월수
		var EndDay = new Date();
		if (selectMonth == '1') {
			EndDay.setMonth(EndDay.getMonth() + 1);

		} else if (selectMonth == '2') {
			EndDay.setMonth(EndDay.getMonth() + 2);

		} else {
			EndDay.setMonth(EndDay.getMonth() + 3);

		}
		EndDay = EndDay.toLocaleDateString();




		if (companyId != "") {

			 // IMP.request_pay(param, callback) 호출
			IMP.init('imp58368556'); //자신의가맹점_키
			var price = 100 //$('#price').text();//결제 금액
			IMP.request_pay({//결제창 호출  // param
				pg: 'inicis', // version 1.1.0부터 지원.  결제를 위한 PG사를 선택
				merchant_uid: 'merchant_' + new Date().getTime(), //가맹점에서 구별할 수 있는 고유한id
				name: '주문명:HomeFix 유료회원 결제',
				buyer_name: companyId, // 구매자 이름
				amount: price, //판매 가격
				

			}, function(rsp) { // callback
				if (rsp.success) { // 결제 성공 시 로직,
				console.log(rsp)
					var msg = '결제가 완료되었습니다.';
					msg += '고유ID : ' + rsp.imp_uid;
					msg += '결제 금액 : ' + rsp.paid_amount;
					msg += '결제수단 :' + rsp.pay_method;
					msg += '업체ID :' + rsp.buyer_name;
					
					msg += '상점 거래ID : ' + rsp.merchant_uid;
					msg += '카드 승인번호 : ' + rsp.apply_num;
					
					
					$.ajax({
						type:'post',
						url:'/payment/paymentInfoInsert',
						data: '' ,
						contenType:'',
						success:function(result){
							
						},
						error:function(err){
							
						}
						
					});
					
					
					
					
					
				} else {    // 결제 실패 시 로직,
					var msg = '결제에 실패하였습니다.';
					msg += '에러내용 : ' + rsp.error_msg;
				}
				alert(msg);
			})
		} else {

			location.href = "/sign/company/sign-in"; //로그인 안했을 시 로그인창으로 이동
		}
	});


	/*3.Import 결제 데이터 불러오기*/
/*$('#cashCheck').click(function(){
	var Iamport = require('iamport');
	var iamport = new Iamport({
		impKey: '6267836153750168', //자신의_API키
		impSecret: '04d582782cf02c1d0a67a5835372a808249322d9514eedf15d6e1eac2ff317efb3c15a3287788692'  //자신의_APIscret_키
	});

	app.get('/payments/status/all', (req, res) => {
		iamport.payment.getByStatus({
			payment_status: 'paid'
		}).then(function(result) {
			res.render('payments_list', { list: result.list });
			console.log('result#########'+result)
		}).catch(function(error) {
			console.log(error);
			red.send(error);
		})
	});

})*/


})