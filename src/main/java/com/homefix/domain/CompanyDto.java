package com.homefix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

//@Table(name = "company")
public class CompanyDto {

	@Id
	@Column(name="cid")
	private String id;
	@Column(name="co_pass")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$/", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String pass;
	
	private String passCheck;
	@Column(name="co_name")
	@Pattern(regexp = "/^[가-힣a-zA-Z0-9]{1,10}$/", message = "한글, 영문 그리고 숫자만 입력 가능합니다.")
	private String name;
	@Column(name="co_ceo")	
	@Pattern(regexp = "/^[가-힣]+$/",message="한글만 입력 가능합니다.")
	private String ceo;
	@Column(name="co_num",unique=true)
	private String num;
	@Column(name="co_tel")
	@Pattern(regexp = "/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/",message="전화번호 형식이 올바르지 않습니다.")
	private String tel;
	@Column(name="co_zip")
	@NotBlank(message = "우편번호는 필수 입력 값입니다.")
	private String zip;
	@Column(name="co_addr")
	@NotBlank(message = "주소는 필수 입력 값입니다.")
	private String addr;
	@Column(name="co_addr_d")
	@NotBlank(message = "상세주소는 필수 입력 값입니다.")
	private String addrd;
	
	
	
	@Column(name="co_email")	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Pattern(regexp = "/^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$/i", message = "이메일 형식이 올바르지 않습니다.")
	private String email;	
	
	
	
	
	
	
	
}
