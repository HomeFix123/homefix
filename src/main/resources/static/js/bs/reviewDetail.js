$(document).ready(function() {
	

	// 시공후기 삭제하기

	$('#deletebtn').click(function() {
		const rid = $('input[name=rid]').val();
		$.ajax({
			type: 'DELETE',
			url: '/review/' + rid,
			success: function(result) {
				console.log("성공");
				location.href = "/review";
			}, error: function(err) {
				console.log(err);
			}
		})


	})

	// 신고하기
	$(document).on('click', '#reportbtn', function() {

		const cid = $('input[name=cid]').val();
		const reason = $('.reason').val();

		$.ajax({
			type: 'POST',
			url: '/review/report/' + cid,
			data: { reason: reason },
			success: function(result) {
				console.log(result);
				if (result == 'true') {
					alert('신고완료');
					$('#reportModal').modal('hide');
				} else if(result == 'notLogin') {
					alert('로그인 해주세요');
					$('#reportModal').modal('hide');
				} else {
					alert('이미 신고한 사람입니다.');
					$('#reportModal').modal('hide');
				}
			},
			error: function(err) {
				console.log(err);

			}
		})

	})


});