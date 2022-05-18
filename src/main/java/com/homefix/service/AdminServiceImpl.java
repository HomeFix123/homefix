package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Payment;
import com.homefix.persistence.CompanyInfoRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.PaymentRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	CompanyRepository companyRepo;
	
	/*
	 * 업체 전체 목록 불러오기
	 * 
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
		
		Company temp = companyRepo.findById(company.getCid()).get();
		temp.setCo_logo(company.getCo_logo());
		temp.setCo_name(company.getCo_name());
		temp.setCo_email(company.getCo_email());
		
		companyRepo.save(temp);
	}
	
	/*
	 * 업체 블랙리스트 지정/해제
	 */
	@Override
	public void enableBlacklist(String cid, Boolean enabled) {
		System.out.println(enabled);
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
	PaymentRepository paymentRepo;
	/*
	 * 결제정보 리스트 불러오기
	 */
	@Override
	public List<Payment> getPaymentList(String cid) {
		
		return paymentRepo.findByCid(cid);
	}

	

	
	

}
