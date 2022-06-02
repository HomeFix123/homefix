$(function() {
	
//-----<Cing.html>
	
	//----CIng.html 견적 클릭시 견적 상세 나오고 스크롤 그 견적상세로 보내기
	$(".progress_space").on("click", function() {
		const eid=$(this).siblings(".eid").val();
		$.ajax({
			type:'get',
			url:'/estimation/estimationDetail',
			data:{id:eid},
			contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
			success:function(result){
				console.log(result);
				$('.ename').text(result.ename);
				$('.etel').text(result.etel);
				$('.space').text(result.space);
				$('.building').text(result.building);
				$('.size').text(result.size);
				$('.start_d').text(result.startDay);
				$('.end_d').text(result.endDay);
				$('.eaddr').text(result.eaddr);
				$('.estyle').text(result.estyle);
				$('.budget').text(result.budget);
			},
			error : function(err) {
				//실패했을 때
    		alert('실패:');
    		console.log(err);
    		}
		})//=====
		
	//마우스 해당 상세보기로 보내기
	let offset = $(".progress_detail").offset();
		$('html,body').animate({ scrollTop: offset.top }, 400);
	});

	//버튼 기능 없애기
	$(".complete_btn.btn.btn-primary").unbind('mouseenter mouseleave');

//==========<Cing.html>
	
	

	//예산 1000단위로 ,찍기
	let budget = $('.budget').text();
	let budget2 = budget.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	$('.budget').text(budget2);


//시공완료버튼 눌렀을 때 db수정하기
	$(".ing_btn").on("click", function(e) {
			e.preventDefault();
			const ingBtn=$(this);
			const eid=$(this).parents('.progress_td').siblings('th').children("div").children("input").val();
			const ing=$(this).parent().siblings('span');
			console.log(eid);
			$.ajax({
				type:'get',
				url:'/estimation/complete',
				data:{id:eid},
				contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
				success:function(){
					ing.text("시공완료");
					ing.addClass("complete_txt");
					ingBtn.addClass("nothover_btn");
					ingBtn.attr("id","nothover_btn");
				},
				error : function(err) {
					//실패했을 때
	    		alert('실패:');
	    		console.log(err);
	    		}
			})//ajax
		 });//===$(".ing_btn") click

//---<ChosenDetail(구  MPickCDetail)>		 
	//-확정하기 버튼 클릭 시 체크박스 유효성검사
	$(".decision_btn_CD").on("click", function(e){
		//기본 이벤트 비활성화
		e.preventDefault();
		const decisionBtn=$(this);
		//유효성 검사
		if($(".warning_check").is(":checked") == true){
    	console.log('체크된 상태');
    	$.ajax({
				type:'get',
				url:'/estimation/confirmation',
				data:{eid:$('.eid').val(),cid:$('.cid').val()},
				contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
				success:function(){
					console.log('성공');
					decisionBtn.text("확정요청완료");
					decisionBtn.addClass("nothover_btn");
					decisionBtn.attr("id","nothover_btn");
				},
				error : function(err) {
					//실패했을 때
	    			alert('실패:');
	    			console.log(err);
	    		}
			})//ajax
		}
		if($(".warning_check").is(":checked") == false){
		alert("필수 사항을 체크해주세요");
    	console.log('체크되지않은 상태');
		}
	});//=확정하기 버튼 클릭 시 체크박스 유효성검사

	//-채팅하기 버튼 클릭시 채팅띄우기(MPickCDetail)
	$(".chat_btn").on("click",function(){
		window.open("http://localhost:3000/chat?roomCode=1&id=1", 'chatting', 'width=500px, height=800px');
	});//=채팅하기 버튼 클릭시 채팅띄우기(MPickCDetail)

//===<ChosenDetail(구  MPickCDetail)>	

});