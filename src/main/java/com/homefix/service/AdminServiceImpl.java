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
