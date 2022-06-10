$(document).ready(function() {
	

	// 좋아요
	$(document).on('click', '.preferSave', function() {
		console.log("저장");
		let preferCnt = Number($('.preferCnt').text());
		const bid = $('input[name=bid]').val();

		const preferck = $('input[name=preferck]').val();
		
		if (preferck == 'false') {
			$.ajax({
				type: 'POST',
				url: '/brag/prefer/' + bid,
				success: function(result) {
					
					$('#prefer').removeClass('far fa-heart');
					$('#prefer').addClass('fas fa-heart');
					$('#love').removeClass('preferSave bg-light');
					$('#love').addClass('preferDelete bg-light');
					$('.preferCnt').text(preferCnt + 1);
					console.log("좋아요 성공");
				}
			})
		}
		$('input[name=preferck]').val('true');
	})

	// 좋아요 취소
	$(document).on('click', '.preferDelete', function() {
		console.log("삭제");
		let preferCnt = Number($('.preferCnt').text());
		const bid = $('input[name=bid]').val();
		const preferck = $('input[name=preferck]').val();
		console.log(preferck);
		if (preferck == 'true') {
			$.ajax({
				type: 'DELETE',
				url: '/brag/prefer/' + bid,
				success: function(result) {
					console.log(result);
					$('#prefer').removeClass('fas fa-heart');
					$('#prefer').addClass('far fa-heart');
					$('#love').removeClass('preferDelete bg-light');
					$('#love').addClass('preferSave bg-light');
					$('.preferCnt').text(preferCnt - 1);
					console.log("좋아요 취소");
				}
			})
		}
		$('input[name=preferck]').val('false');
	})

	// 자랑 삭제하기
	$('#deletebtn').click(function() {
		const bid = $('input[name=bid]').val();
		$.ajax({
			type: 'DELETE',
			url: '/brag/' + bid,
			success: function(result) {
				console.log("성공");
				$('#deleteModal').modal('hide');
			}, error: function(err) {
				console.log(err);
			}
		})


	})

	// 신고하기
	$(document).on('click', '#reportbtn', function() {
		
		// 글쓴이와 사유 저장
		const id = $('input[name=id]').val();
		const reason = $('.reason').val();

		
		$.ajax({
			type: 'POST',
			url: '/brag/report/' + id,
			data: {
				reason: reason
			},
			success: function(result) {
				//신고 여부 반환
				if (result == 'true') {
					alert('신고완료');
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