package com.homefix.service;

import java.util.List;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Review;

public interface CompanyService {

	// 사업자 아이디 중복 조회
	public String idCheck(String id);

	// 사업자 이메일 중복 조회
	public String emailCheck(String email);

	// 사업자번호 중복 조회
	public String companyNumberCheck(String num);

	// 로그인 성공
	public String login(Company com);

	// 사업자 회원 탈퇴
	public String companyDelete(Company com);

	// 사업자 정보 조회
	public Company getCompanyMyInfo(String companyId);

	// 사업자 정보수정
	public void companyUpdate(Company com);

	// 사업자 회원가입 
	public void companyInsert(Company com);

	// 시공전문가(업체소개)
	public CompanyInfo getCompanyIntroduction(Company com);

	// 시공전문가(전문분야)
	//public CompanySpecial getCompanySpecial(Company com);

	//시공전문가(인테리어자랑) 
	public List<Brag> getInteriorBrag(Company com);
	
	//시공전문가(업체후기)
	public List<Review> getCompanyReview(Company com, int page);
	// public Map<String, String> validateHandling(Errors errors);
 
}
