/**
 * @author 이영은
 */

//---------------------------------------------$(function(){})---------------------------------------------
$(function(){
	//-sa-dropdown(알림) 버튼 외 누를 시 드롭다운 해제
	$(document).mouseup(function (e){
	  	var LayerPopup = $(".sa-dropdown");
	  	if(LayerPopup.has(e.target).length === 0){
	    	$(".chatList").css({"visibility":"hidden"
							,"opacity": "0"
							,"-webkit-transform": "rotateX(-75deg)"
							,"-moz-transform":"rotateX(-75deg);"
							,"-ms-transform":"rotateX(-75deg)"
							,"-o-transform":"rotateX(-75deg)"
							,"transform":"rotateX(-75deg)"
			})//=css
	  	}
	});
	//=sa-dropdown(알림) 버튼 외 누를 시 드롭다운 해제
	
	//---------------------------------------------알림버튼 누를 시 채팅 불러오기--------------------------------------------- 
	$(".sa-dropdown").on("click",function(){
		console.log("하이");
		/*-1.ajax*/
		$.ajax({
			type:'get',
			url:'/chat/getChatList',
			contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
			success:function(result){
				//성공하면 원래 있는 리스트 비우기
				$("#global-list").empty();
				console.log('채팅컨트롤러가기성공');
				//대화중인 채팅방이 없을 경우
				if(result.length == 0){
					console.log("데이터 없음")
						let tag = '<li class="remove-item"><span class="remove-icon">'
									+ '</span>'
									+ '<div class="sa-course">'
									+ '<div class="course-info">'
									+ '<div class="info">'
									+ '<h2 class="title">'
									+ '<a id="gochat">'
									+ '진행 중인 대화가 없습니다.' 
									+'</a>'
									+ '</h2>'
									+ '</div>'
									+ '</div>'
									+ '</div>'
									+ '</li>';
						console.log(tag);
						$("#global-list").append(tag);
				//대화중인 채팅방이 있을 경우
				}else{
					console.log(result);
					console.log(result.user);
					console.log(result.member);
					console.log(result.list.length)
					//-회사 로그인 했을 경우 
					if(result.user == "company"){
						console.log("회사다");
						//-해당 채팅 html에 붙이기 for문
						for(let i=0 ; i < result.list.length ; i++){
							console.log(i);
							let tag = '<li class="remove-item"><span class="remove-icon">'
										+ '<i class="bi bi-chat-square-text"></i>'
										+ '</span>'
										+ '<div class="sa-course">'
										+ '<div class="course-info">'
										+ '<div class="info">'
										+ '<h2 class="title">'
										+ '<a id="gochat">'
										+ result.list[i].member.name 
										+'</a>'
										+ '<input type="hidden" class="mid" value='+ result.list[i].member.id +'>'
										+ '<input type="hidden" class="cid" value='+ result.list[i].company.id +'>'
										+ '<input type="hidden" class="nickname" value='+ result.list[i].company.name +'>'
										+ '</h2>'
										+ '</div>'
										+ '</div>'
										+ '</div>'
										+ '</li>';
							console.log(tag);
							$("#global-list").append(tag);
								
							//-회사일때 채팅하기 버튼 클릭시 방만들고 채팅띄우기(MPickCDetail)
							$("#gochat").on("click",function(e){
								//기본 이벤트 비활성화
								e.stopPropagation();
								let mid = $(this).siblings(".mid").val()
								let cid = $(this).siblings(".cid").val()
								let nickname = $(this).siblings(".nickname").val()
						    	//-ajax로 db저장 후 불러오기 
						    	$.ajax({
										type:'get',
										url:'/estimation/saveChatRoom',
										data:{id:mid,cid:cid,nickname:nickname},
										contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
										success:function(result){
											const roomCode=result.room_id;
											const id = result.company.id;
											const nickname = result.company.name;
											window.open("http://3.39.226.147:3000/chat?room="+roomCode+"&id="+id+"&nickname="+nickname, 'chatting', 'width=500px, height=800px');
											
										},
										error : function(err) {
											//실패했을 때
							    			alert('실패:');
							    			console.log(err);
		    							}//error
								})//=ajax로 db저장 후 불러오기
							});//=회사일때 채팅하기 버튼 클릭시 방만들고 채팅띄우기(MPickCDetail)
						}//=해당 채팅 html에 붙이기 for문
					//-고객(member) 로그인 했을 경우 if
					}else if(result.member == "member"){
						console.log("고객이다");
						for(let i=0 ; i < result.list.length ; i++){
							console.log(i);
							let tag = '<li class="remove-item"><span class="remove-icon">'
										+ '<i class="bi bi-chat-square-text"></i>'
										+ '</span>'
										+ '<div class="sa-course">'
										+ '<div class="course-info">'
										+ '<div class="info">'
										+ '<h2 class="title">'
										+ '<a id="gochat">'
										+ result.list[i].company.name 
										+'</a>'
										+ '<input type="hidden" class="mid" value='+ result.list[i].member.id +'>'
										+ '<input type="hidden" class="cid" value='+ result.list[i].company.id +'>'
										+ '<input type="hidden" class="nickname" value='+ result.list[i].member.name +'>'
										+ '</h2>'
										+ '</div>'
										+ '</div>'
										+ '</div>'
										+ '</li>';
							console.log(tag);
							$("#global-list").append(tag);
							
							/*-고객일때 채팅하기 버튼 클릭시 방만들고 채팅띄우기(MPickCDetail)*/
							$("#gochat").on("click",function(e){
								//기본 이벤트 비활성화
								e.stopPropagation();
								//console.log("채팅방으로 가자");
								let mid = $(this).siblings(".mid").val()
								let cid = $(this).siblings(".cid").val()
								let nickname = $(this).siblings(".nickname").val()
						    	//-(고객)ajax로 db저장 후 불러오기 
						    	$.ajax({
										type:'get',
										url:'/estimation/saveChatRoom',
										data:{id:mid,cid:cid,nickname:nickname},
										contentType : 'applicaton/x-www-form-urlencoded;charset=utf-8',
										success:function(result){
											const roomCode=result.room_id;
											const id = result.member.id;
											const nickname = result.member.name;
											window.open("http://3.39.226.147:3000/chat?room="+roomCode+"&id="+id+"&nickname="+nickname, 'chatting', 'width=500px, height=800px');
										}, 
										error : function(err) {
											//실패했을 때
							    			alert('실패:');
							    			console.log(err);
							    		}//==error
									})//==(고객)ajax로 db저장 후 불러오기 
							});/*=고객일때 채팅하기 버튼 클릭시 방만들고 채팅띄우기(MPickCDetail)*/
		
							/*-모달 이벤트 버블링 없애기*/
							$(".chatList").on("click",function(e){
								e.stopPropagation();
							});/*=모달 이벤트 버블링 없애기*/
						}/*=member일경우의 for문*/
					}//=고객(member) 로그인 했을 경우 if
				}//=else(채팅방이 존재할 경우)
			}, //=success
			error : function(err) {
				//실패했을 때
    			alert('실패:');
    			console.log(err);
    		} //=error
		})
		/*=1.ajax*/
		
		/*css 변경*/
		$(".chatList").css({"visibility":"visible"
							,"opacity": "1"
							,"-webkit-transform": "rotateX(0deg)"
							,"-moz-transform":"rotateX(0deg);"
							,"-ms-transform":"rotateX(0deg)"
							,"-o-transform":"rotateX(0deg)"
							,"transform":"rotateX(0deg)"
		})//=css
	})	
	//=============================================알림버튼 누를 시 채팅 불러오기=============================================	
	
	
})
//=============================================$(function(){})=============================================


/**
 * @author 이건희
 */

// 마우스 오버시 로고 변경
$(function(){
	$('#headerLogo').hover(function(){
		$(this).attr('src', '/images/homefix_hover.png')
	}, function(){
		$(this).attr('src', '/images/homefix.png')
	})
})