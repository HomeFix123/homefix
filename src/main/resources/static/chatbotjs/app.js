
var stompClient = null;


//텍스트창에 글을 쓰면 커넥트 버튼이 눌림
/*function handleInputOnkeyup()  {
  document.getElementById('connect').click();
}*/
$('#chatbot').hide()
//창이 열릴때 connect 버튼 눌림 처리
let chatbotLoad = false;
const chatbotCallBtn = document.getElementById('chatbotCallBtn');
chatbotCallBtn.onclick = function(event) {
	event.preventDefault();
	if(!chatbotLoad){
		document.getElementById('connect').click();	
		$('#chatbot').show()
		chatbotLoad = true;
	} else {
		document.getElementById('disconnect').click();
		$('#chatbot').hide()
		chatbotLoad = false;	
	}
  
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function (message) {
            showBotMessage(message.body); //서버에 메시지 전달 후 리턴받는 메시지
            $('#communicate').scrollTop($('#communicate')[0].scrollHeight);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    let message = $("#msg").val()
    showMessage(message);

    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
}
//텍스트 필드에 써서 보낸 내 질문
function showMessage(message) {
    $("#communicate").append("<div class='direct-chat-msg right'>" + "<div class='direct-chat-text bg-primary ml-5' id='meText'>" + message + "</div>"+ "</div>");
}

//리턴하는 AI의 답변!	//위와 말풍선을 나누기위해 따로 작성했다! 제목으로 구분됨!
function showBotMessage(message) {
    $("#communicate").append("<div class='direct-chat-msg'>" +"<span class='name ml-3 mt-2'><h5>Home-Fix</h5></span>" +"<div class='direct-chat-text mr-0 mb-2' id='botText'>" + message + "</div>"+ "</div>");
}

window.onload = function(){
    $("#chatbot form").on('submit', function (e) {
        e.preventDefault();
        $('#msg').val("") 
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
};
