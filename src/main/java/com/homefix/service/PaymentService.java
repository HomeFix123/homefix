package com.homefix.service;

import java.util.List;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;

public interface PaymentService {

	public void paymentInfoInsert(Payment vo) ;
	
	public List<Payment> RemainingDate(Company company);
}
  