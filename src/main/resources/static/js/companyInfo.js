
$(function(){
	const cityDiv = $('#citiesList');
	const countries = $('.country');
	countries.hide();
	cityDiv.find('a').click(function(event){
		event.preventDefault();
		$(cityDiv).find('.active').removeClass('active')
		$(this).addClass('active');
		const cityName = $(this).text();
		countries.hide();
		$('#' + cityName).show();
	})
	
	const spacesDiv = $('#spacesDiv')
	countries.find('a').click(function(event){
		event.preventDefault();
		const city = $(this).parent().attr('id');
		const id = $(this).attr('id');
		const name = $(this).text();
		addAreaBtns(id, city, name);
		
		
	})
	
	function addAreaBtns(id, city, name){
		if(name == null){
			name = '전체'
		}
		let div = "<div class='alert alert-primary alert-dismissible fade show m-2' role='alert'>"
		div += city + ' ' + name
		div += "<input type='hidden' name='spacesArr' value='" + id + "'>"
		div += "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button></div>"
		
		spacesDiv.append(div);
	}
	
	const form = $('#writeinfo');
	const img = $('#cinfo_img'); 
	$('#submitBtn').click(() => {
		const imageInput = $('#inputImage')[0];
		const formData = new FormData();
		if(imageInput.files[0] != null){
			const imgName = uuid(imageInput.files[0].name);
			formData.append("file", imageInput.files[0], imgName);	
			
			const result = saveImage(formData);
			if(result){
				img.val(imgName);
			}
		}
      	
		
		form.submit()
	})
	
	// uuid 생성
	function uuid(file_nm) {
		const s4 = () => ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1)
		
		return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4() + file_nm.substr(file_nm.indexOf("."), file_nm.length-1).toLowerCase();
	}
	
	function saveImage(formData){
		let answer = false;
		$.ajax({
			type: "POST",
			url: "http://140.238.11.118:5000/upload",
			processData: false,
			contentType: false,
			data: formData,
			async: false
		}).done(() => {
			answer = true;
		}).fail(() => {
			
		})
		
		return answer;
	}
	
	const specialArr = companyInfo.specialty;
	for(let special of specialArr){
		if(special.sp_cont == '주거공간'){
			$('input#homeSpecial').prop("checked", true)	
		} else {
			$('input#comSpecial').prop("checked", true)	
		}
			
	}
	
	const areaArr = companyInfo.area;
	for(let area of areaArr){
		const id = area.area.id
		const city = area.area.aname
		const name = area.area.dt_area
		addAreaBtns(id, city, name);
	}
	
})


