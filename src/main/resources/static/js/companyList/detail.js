$(function(){
	const id = $('#companyBookMark .company-id').val();
	$('#bookMarkBtn').click(function(event){
		const bookMarkIcon = $(this).find('i');
		const isBookMark = bookMarkIcon.hasClass('fas')
		
		event.preventDefault();
		$.ajax({
			type:"put",
			url:"/company/" + id,
			data: {
				bookMark: isBookMark
			}
		}).done(function(result){
			if(result == "200"){
				if(isBookMark){
					bookMarkIcon.removeClass('fas')
					bookMarkIcon.addClass('far')	
				} else {
					bookMarkIcon.removeClass('far')
					bookMarkIcon.addClass('fas')
				}
					
			}
			
			
		}).fail(function(err){
			console.log(err);
		})
	})
})