package com.homefix.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletResponse;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyDto;

public interface CompanyService {


	// 사업자 아이디 중복 조회
	public String  idCheck(String id); 
	
	

	// 사업자 이메일 중복 조회
	public String emailCheck(String email);

	// 사업자번호 중복 조회
	public String companyNumberCheck(String num);

	//로그인 성공
	public String login(Company com);

	// 사업자 회원 탈퇴
	public void companyDelete(String id);

	//사업자 정보 조회
	public Company getCompanyMyInfo(String companyId);
	
	
	// 사업자 정보수정
	public void companyUpdate(CompanyDto dto);

	// 사업자 회원가입
	public void companyInsert(Company com);

	
	
	public Map<String, String> validateHandling(Errors errors);
	
	 
}
