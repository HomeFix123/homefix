package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.Member;
import com.homefix.domain.Payment;
import com.homefix.persistence.CompanyInfoRepository;
import com.homefix.persistence.CompanyReportRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PaymentRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	MemberRepository memberRepo;
	
	/*
	 * 고객 전체 목록 불러오기
	 */
	@Override
	public List<Member> getMemberList(){
		
		return (List<Member>)memberRepo.findAll();
	}
	
	/*
	 * 고객 아이디로 고객 정보 가져오기
	 */
	@Override
	public Member getMember(String id) {
		
		return memberRepo.findById(id).get();
	}

	/*
	 * 고객 정보 수정
	 */
	@Override
	public void updateMember(Member member) {
		Member temp = memberRepo.findById(member.getId()).get();
		System.out.println(temp);
		temp.setEmail(member.getEmail());
		temp.setNickname(member.getNickname());
		temp.setProfilimg(member.getProfilimg());
		
		memberRepo.save(temp);
		
	}

	/*
	 * 고객 정보 수정
	 */
	@Override
	public void enableBlackMember(String id, Boolean enabled) {
		Member temp = memberRepo.findById(id).get();
		temp.setEnabled(enabled);
		
		memberRepo.save(temp);
	}
	
	
	
	@Autowired
	CompanyRepository companyRepo;
	
	/*
	 * 업체 전체 목록 불러오기
	 */
	@Override
	public List<Company> getCompanyList() {
		
		return (List<Company>) companyRepo.findAll();
	}
	
	/*
	 * 업체 아이디로 정보 불러오기
	 */
	@Override
	public Company getCompany(String cid) {
		
		return companyRepo.findById(cid).get();
	}
	
	/*
	 * 업체 정보 수정
	 */
	@Override
	public void updateCompany(Company company) {
		
		Company temp = companyRepo.findById(company.getId()).get();
		temp.setLogo(company.getLogo());
		temp.setName(company.getName());
		temp.setEmail(company.getEmail());
		
		companyRepo.save(temp);
	}
	
	/*
	 * 업체 블랙리스트 지정/해제
	 */
	@Override
	public void enableBlacklist(String cid, Boolean enabled) {
		Company company = companyRepo.findById(cid).get();
		company.setEnabled(enabled);
		companyRepo.save(company);
		
	}
	
	
	@Autowired
	CompanyInfoRepository companyDetailRepo;
	
	/*
	 * 업체 상세정보 불러오기
	 */
	@Override
	public CompanyInfo getCompanyDetail(String cid) {
		Company co = companyRepo.findById(cid).get();
		return companyDetailRepo.findByCompany(co).get(0);
	}
	
	
	@Autowired
	CompanyReportRepository companyReportRepo;
	/*
	 * 업체 신고 목록 불러오기
	 */
	@Override
	public List<CompanyReport> getCompanyReportList() {
		
		return (List<CompanyReport>) companyReportRepo.findAll();
	}
	
	/*
	 * 업체 신고 삭제
	 */
	@Override
	public void deleteReport(String rid) {
		Integer id = Integer.parseInt(rid);
		CompanyReport report = companyReportRepo.findById(id).get();
		companyReportRepo.delete(report);
	}
	
	@Autowired
	PaymentRepository paymentRepo;
	/*
	 * 결제정보 리스트 불러오기
	 */
	@Override
	public List<Payment> getPaymentList(String cid) {
		
		return null;
	}

	
	
	

}
