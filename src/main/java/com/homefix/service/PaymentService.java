package com.homefix.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;

public interface PaymentService {

	public void paymentInfoInsert(Payment vo) ;
	
	public List<Payment> RemainingDate(Company company);
	
	public List<Payment> RemainDate(Company company, Integer page);
	
	
	
	//public List<Payment> getPaymentPage(int page, Company company);
}
  