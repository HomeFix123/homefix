package com.homefix.service;

import java.util.List;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;
import com.homefix.domain.Payment;
import com.homefix.domain.Tip;

public interface AdminService {
	
	// 고객 목록 불러오기
	public List<Member> getMemberList();
	
	// 고객 아이디로 정보 불러오기
	public Member getMember(String id);
	
	// 고객 정보 수정
	public void updateMember(Member member);
	
	// 고객 블랙리스트 지정/해제
	public void enableBlackMember(String id, Boolean enabled);
	
	// 고객 신고 목록 불러오기
	public List<MemberReport> getMemberReportList();
	
	// 고객 신고 삭제
	public void deleteMemberReport(String rid);
	
	// 고객 오늘의 신고 개수
	public Long countTodayMemberReport();
	
	// 고객 개인 신고 정보
	public List<MemberReport> getMemberReport(String id);
	
	// 업체 목록 불러오기
	public List<Company> getCompanyList();
	
	// 업체 아이디로 정보 불러오기
	public Company getCompany(String cid);
	
	// 업체 정보 수정
	public void updateCompany(Company company);
	
	// 업체 블랙리스트 지정/해제
	public void enableBlacklist(String cid, Boolean enabled);
	
	// 업체 상세정보 불러오기
	public CompanyInfo getCompanyDetail(String cid);
	
	// 업체 결제정보 불러오기
	public List<Payment> getPaymentList(String cid);
	
	// 고객 오늘의 신고 개수
	public long countTodayCompanyReport();
	
	// 업체 신고 목록 불러오기
	public List<CompanyReport> getCompanyReportList();
	
	// 업체 신고 삭제
	public void deleteCompanyReport(String rid);
	
	// 업체 개인 신고 정보
	public List<CompanyReport> getCompanyReport(String id);
	
	
	// 자랑 게시글 목록 불러오기
	public List<Brag> getBragList(int page);
	
	// 자랑 게시글 개수 불러오기
	public long countBragList();
	
	// 팁 게시글 목록 불러오기
	public List<Tip> getTipList(int page);
	
	// 팁 게시글 개수 불러오기
	public long countTipList();
	
	// 월별 회원가입수
	public List<Object[]> aggregateNewUser();
	
	// 월별 매출액
	public List<Object[]> aggregatePayments();
	
	// 작성 견적글 불러오기
	public List<Estimation> getEstimationList(String id);
	
	// 주간 신규 유저수
	public long countNewUser();
	
	// 월정액 유지수
	public long countPayUser();
	
	// 이번달 계약성공건수
	public long countContractMonth();
}
