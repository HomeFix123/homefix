/*  */
$(function() {
	
	// 견적 상세 페이지==========================================
	//-확정하기 버튼 클릭 시
	$(".decision_btn").on("click", function(e){
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
					//버튼 css 변경
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
	
	// 채팅하기 버튼 클릭시 채팅방 띄우기
	$(".chat_btn").on("click",function(e){
		//기본 이벤트 비활성화
		e.preventDefault();
		if($(".warning_check").is(":checked") == true){
    	console.log('체크된 상태');
    	//ajax로 db저장 후 불러오기 
    	$.ajax({
				type:'get',
				url:'/estimation/saveChatRoom',
				data:{id:$('.mid').val(),cid:$('.cid').val(),nickname:$('.cname').val()},
				contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
				success:function(result){
					console.log('성공');
					console.log(result);
					console.log(result.room_id);
					console.log(result.company.id);
					console.log(result.company.name);
					const roomCode=result.room_id;
					const id = result.company.id;
					const nickname = result.company.name;
					window.open("http://3.39.226.147:3000/chat?room="+roomCode+"&id="+id+"&nickname="+nickname, 'chatting', 'width=500px, height=800px');
					
				},
				error : function(err) {
					//실패했을 때
	    			alert('실패:');
	    			console.log(err);
	    		}
			})//ajax
    	
    	}
    	else{alert("필수 사항을 체크해주세요");
    	console.log('체크되지않은 상태');
    	}
	
	
	});
	
	
	
})


