package com.homefix.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {

	//@NotBlank(message = "아이디는 필수 입력 값입니다.")
	//@Pattern(regexp = "/^[a-zA-z0-9]{4,12}$/", message = "아이디 형식이 올바르지 않습니다.")
	private String id;
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$/", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String pass;
	
	private String passCheck;
	
	@Pattern(regexp = "/^[가-힣a-zA-Z0-9]{1,10}$/", message = "한글, 영문 그리고 숫자만 입력 가능합니다.")
	private String name;
	
	@Pattern(regexp = "/^[가-힣]+$/",message="한글만 입력 가능합니다.")
	private String ceo;
	
	private String num;
	
	@Pattern(regexp = "/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/",message="전화번호 형식이 올바르지 않습니다.")
	private String tel;
	
	@NotBlank(message = "우편번호는 필수 입력 값입니다.")
	private String zip;
	@NotBlank(message = "주소는 필수 입력 값입니다.")
	private String addr;
	@NotBlank(message = "상세주소는 필수 입력 값입니다.")
	private String addrd;
	
	
	
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Pattern(regexp = "/^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$/i", message = "이메일 형식이 올바르지 않습니다.")
	private String email;	
	
	
	
	
	
	
	
}
