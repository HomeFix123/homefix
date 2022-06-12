$(function(){
		$("#memberLogin").click(function(){
			$.ajax({
				url : "/sign/member/loginMem",
				type : "POST",
				data : {
					id : $("#id").val(),
					email : $("#email").val()
				},
				success : function(result) {
					alert(result);
				},
			})
			window.location = "/sign/sign-in";
		});
	})