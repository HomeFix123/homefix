package com.homefix.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;
import com.homefix.persistence.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository repo;

	public void paymentInfoInsert(Payment vo) {
		repo.save(vo);

	};
	
	public List<Payment> RemainingDate(Company company) {
		
		return repo.findByCompanyOrderByCompanyDesc(company);
	}
	  
   
}   
  