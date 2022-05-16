package com.homefix.service;

import java.util.List;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Payment;

public interface AdminService {
	
	// 업체 목록 불러오기
	public List<Company> getCompanyList();
	
	// 업체 상세정보 불러오기
	public CompanyInfo getCompanyDetail(String cid);
	
	// 업체 결제정보 불러오기
	public List<Payment> getPaymentList(String cid);
	
}
