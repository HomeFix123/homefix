$(function(){
	const id = "test";
	$.ajax({
		url: "/admin/member/" + id,
		contentType: "application/json"
	}).done((result) => {
		console.log(result)
	}).fail((error) => {
		console.log(error)
	});
});