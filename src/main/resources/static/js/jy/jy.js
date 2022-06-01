/*  */
$(function() {
	//평수 입력시 ㎡ 자동입력 ===========================================
	$("#inSize").keyup(function() {
		let input = $("#inSize").val();
		let output = input * 3.31; //3.305785
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
	function invalidItem() {
		
		//공간, 건물유형 유효성 검사
		if($("input[name=space]:radio:checked").length<1){
			$('.space_error_box').text("공간유형을 선택해주세요.");
			window.scrollTo({top:0, left:0, behavior:'auto'}); //화면 위로 이동
			return false;
		} else{
			$('.space_error_box').text("");
			
		}
		
		if($("input[name=building]:radio:checked").length<1){
			$('.building_error_box').text("건물유형을 선택해주세요.");
			window.scrollTo({top:250, left:0, behavior:'auto'});
			return false;
		 } else{
			$('.building_error_box').text("");
		}
	}
	
	$('#subbutton').click(function() {
		invalidItem();
		
		//================ 평수 유효성검사 ================== 
		if(inSize.value ==''){            
			$('.size_error_box').text("평수를 입력해주세요.");
			$('#inSize' ).focus();
			window.scrollTo({top:300, left:0, behavior:'auto'});
			return false;        
		} else{
			$('.size_error_box').text("");
		}
		//==============================================
		
		//================ 예산 유효성검사 ================== 
		if(budget.value ==''){            
			$('.budget_error_box').text("예산을 입력해주세요.");
			$('#budget' ).focus();
			return false;        
		} else{
			$('.budget_error_box').text("");
		}
		//==============================================
		
		//================ 주소 유효성검사 ================== 
		if(sample6_address.value ==''){            
			$('.eaddr_error_box').text("주소를 입력해주세요.");
			$('#sample6_address' ).focus();
			return false;        
		} else{
			$('.eaddr_error_box').text("");
		}
		//==============================================
		
		//============== 이름 유효성검사 ==================                
		 
		var name = /^[가-힣]+$/;
		var tel = /^[0-9]{2,3}[0-9]{3,4}[0-9]{4}/;
		
		if(ename.value ==''){            
			$('.ename_error_box').text("이름을 입력해주세요.");
			$('#ename' ).focus();
			return false;        
		} else{
			$('.ename_error_box').text("");
		}          
			
		if(!name.test(ename.value)){            
			$('.ename_error_box').text("특수문자, 영어, 숫자는 사용할수 없습니다. 한글만 입력해주세요.");
			$('#ename' ).focus();
			return false;        
		} else{
			$('.ename_error_box').text("");
		}   
		//================================================
		
		//================ 전화번호 유효성검사 ================== 
		if(etel.value ==''){            
			$('.etel_error_box').text("전화번호를 입력해주세요.");
			$('#etel' ).focus();
			return false;        
		} else{
			$('.etel_error_box').text("");
			
		}        
			
		if(!tel.test(etel.value)){            
			$('.etel_error_box').text("형식이 올바르지 않습니다. 예) 01012345678");
			$('#etel' ).focus();
			return false;        
		} else{
			$('.etel_error_box').text("");
			
		}   
		
		//================ 희망일 유효성검사 ================== 
		if(startDay.value == '' || endDay.value == ''){            
			$('.day_error_box').text("희망일을 입력해주세요.");
			//$('#startDay' ).focus();
			window.scrollTo({top:800, left:0, behavior:'auto'});
			return false;        
		} else{
			$('.day_error_box').text("");
		}
		//==============================================
		
		alert('견적상담 신청이 완료되었습니다.');
		
				
		
	})
	
	
	
	
	
	
	
	
})


