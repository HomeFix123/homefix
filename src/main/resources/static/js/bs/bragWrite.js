$(document).ready(function() {
	
	// 웹 에디터 Summernote
	$('#summernote').summernote({
		tabsize: 2,
		minHeight: 500,
		toolbar: [
			// [groupName, [list of button]]
			['fontname', ['fontname']],
			['fontsize', ['fontsize']],
			['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
			['table', ['table']],
			['para', ['ul', 'ol', 'paragraph']],
			['height', ['height']],
			['insert', ['picture', 'link']],
			['view', ['help']]
		],
		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
		fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],

		lang: 'ko-KR',
		placeholder: '최대 2048자까지 쓸 수 있습니다.',
		// 이미지 저장을 위한 콜백 기능		
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
				// 다중 이미지 처리를 위한 for문
				for (let i = files.length - 1; i >= 0; i--) {
					sendFile(files[i], this);
				}
			}
		}

	});
	
	// 인테리어 자랑 세부사항 펼치기
	$(".writedetailbtn").on("click", function() {
		$(".writedetail").slideToggle();
	})

	 //섬네일 이미지 미리보기
	function readImage(input) {
		if (input.files && input.files[0]) {
			const reader = new FileReader();

			reader.onload = (e) => {
				const previewImage = document.getElementById('previewImage');
				previewImage.src = e.target.result;
			}
			reader.readAsDataURL(input.files[0]);


		}
	}


	// 이벤트 리스너
	document.getElementById('inputImage').addEventListener('change', (e) => {
		readImage(e.target);

	})


	// 작성하기
	$('.writebtn').click(function() {
		const imageInput = $('#inputImage')[0];
		const title = document.querySelector('.writeTitle');
		const cid = $('select[name="cid"]');

		// 파일을 여러개 선택할 수 있으므로 files 라는 객체에 담긴다.
		console.log("imageInput: ", imageInput.files)
		
		//유효성 검사
		if (title.value.trim() < 1) {
			title.focus();
			alert("제목을 입력해주세요.");
			return;
		}
		if (cid.val() == null) {
			alert("업체를 선택해주세요.");
			cid.focus();
			return;
		} 
		if ($('#hometype').val()==null){
			alert("주거형태를 선택해주세요.");
			$('#hometype').focus();
			return;
		}
		
		if ($('#bsize').val()==null || $('#bsize').val()==""){
			alert("평수를 입력해주세요.");
			$('#bsize').focus();
			return;
		} else if($('#bsize').val().search(/[0-9]/g)){
			alert("숫자만 입력해주세요.");
			$('#bsize').focus();
			return;
		}
		
		if ($('#bbudget').val()==null || $('#bbudget').val()==""){
			alert("예산을 입력해주세요.");
			$('#bbudget').focus();
			return;
		} else if($('#bbudget').val().search(/[0-9]/g)){
			alert("숫자만 입력해주세요.");
			$('#bbudget').focus();
			return;
		}
		
		if ($('#job').val()==null){
			alert("작업분야를 선택해주세요.");
			$('#job').focus();
			return;
		}
		
		if ($('#family').val()==null){
			alert("가족형태를 선택해주세요.");
			$('#family').focus();
			return;
		}
		
		if ($('#worker').val()==null){
			alert("작업자를 선택해주세요.");
			$('#worker').focus();
			return;
		}
		if ($('#loc').val()== null || $('#loc').val()==""){
			alert("지역을 작성해주세요.");
			$('#loc').focus();
			return;
		} else if(/[a-zA-Z가-힣0-9]{2, }/.test($('#loc').val())){
			alert("지역을 2글자 이상 제대로 작성해주세요.");
			$('#loc').focus();
			return;
		}

		if (imageInput.files.length === 0) {
			alert("대표사진을 넣어주세요.");
			return;
		}


		const formData = new FormData();
		// 이미지 파일명 uuid 적용
		formData.append("file", imageInput.files[0], uuid(imageInput.files[0].name));
		// 이미지 파일명 저장
		$('#bimgadr').val(formData.get('file').name);

		// 이미지 파일 이미지 웹서버로 전송
		$.ajax({
			type: "POST",
			url: "http://140.238.11.118:5000/upload",
			// processData, contentType 없으면 에러
			processData: false,
			contentType: false,
			data: formData,
			success: function(result) {
				console.log("success: ",result)
			},
			err: function(err) {
				console.log("err:", err)
			}
		})




		$('#writeinfo').submit();

	})

	// uuid 생성
	function uuid(file_nm) {
		function s4() {
			return ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1);
		}
		return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + file_nm.substr(file_nm.indexOf("."), file_nm.length - 1).toLowerCase();
	}

	// summernote 다중 이미지 저장
	function sendFile(file, el) {
		let formData = new FormData();
		formData.append('file', file, uuid(file.name));
		console.log("uuid 적용확인: ", formData.get('file').name);
		$.ajax({
			type: "POST",
			url: "http://140.238.11.118:5000/upload",
			data: formData,
			processData: false,
			contentType: false,
			success: function(result) {
				console.log("result 값 확인: ", formData.get('file').name);
				$(el).summernote('insertImage', 'http://140.238.11.118:5000/upload/' + result);
				$('#imageBoard > ul').append('<img src="' + result + '" width="auto" height="auto"/>');
				console.log("성공");
			},
			err: function(err) {
				console.log("에러: ", err)
			}
		})
	}

});