/*  */
$(function() {
	//평수 입력시 ㎡ 자동입력 ===========================================
	$("#inSize").keyup(function() {
		let input = $("#inSize").val();
		let output = input * 3.305785;
		$("#outSize").val(output);
	});
	//=============================================================

	/* 달력 시작, 종료일 설정 */

	//오늘 날짜를 출력
	$("#today").text(new Date().toLocaleDateString());

	//datepicker 한국어로 사용하기 위한 언어설정
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	// 시작일(startDay)은 종료일(endDay) 이후 날짜 선택 불가
	// 종료일(endDay)은 시작일(startDay) 이전 날짜 선택 불가

	//시작일.
	$('#startDay').datepicker({
		showOn: "focus",                     // 달력을 표시할 타이밍 (both: focus or button)
		buttonImage: "../images/calendar.png", // 버튼 이미지
		buttonImageOnly: true,             // 버튼 이미지만 표시할지 여부
		//buttonText: "날짜선택",             // 버튼의 대체 텍스트
		dateFormat: "yy-mm-dd",             // 날짜의 형식
		changeMonth: true,                  // 월을 이동하기 위한 선택상자 표시여부
		minDate: 0,                       // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
		onClose: function(selectedDate) {
			// 시작일(startDay) datepicker가 닫힐때
			// 종료일(endDay)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
			$("#endDay").datepicker("option", "minDate", selectedDate);
		}

	});

	//종료일
	$('#endDay').datepicker({
		showOn: "focus",
		buttonImage: "../images/calendar.png",
		buttonImageOnly: true,
		//buttonText: "날짜선택",
		dateFormat: "yy-mm-dd",
		changeMonth: true,
		//minDate: 0, // 오늘 이전 날짜 선택 불가
		onClose: function(selectedDate) {
			// 종료일(endDay) datepicker가 닫힐때
			// 시작일(startDay)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
			$("#startDay").datepicker("option", "maxDate", selectedDate);
		}
	});

	$('img.ui-datepicker-trigger').css({ 'cursor': 'pointer', 'margin-left': '5px' });  //아이콘(icon) 위치

	//===========================================================================

	// 견적상담 입력 유효성 검사
	$('#subbutton').click(function() {
		invalidItem();
	})
	
	function invalidItem() {
		if($("input[name=space]:radio:checked").length<1){
			$('.error_box1').text("공간유형을 선택해주세요.");
			$('.error_box1' ).focus();
			return false;
		}
		
		if($("input[name=building]:radio:checked").length<1){
			alert("건물유형을 선택하세여.");
			return false;
		}
		
		
		
		
	}		
	
	
		
		
		
	
	
	
	

/*if(selectSpace ==""){
				$('#spaceTitle .error_box'  ).html("이름 형식이 올바르지 않습니다.");
				$('#spaceTitle' ).focus();
				return false;
		}*/
	

})


