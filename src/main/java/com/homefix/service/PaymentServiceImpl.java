package com.homefix.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	// 사업자 결제정보 조회 
	public List<Payment> RemainingDate(Company company) {
		
		return repo.findByCompany(company);
	}

	
	  public List<Payment> RemainDate( Company company, Integer page) { int
	  showCntPerPage = 6; 
	  
	  Pageable pageable = (Pageable) PageRequest.of(page - 1, showCntPerPage,
	  Sort.by("pday").descending());
	  return repo.findByCompanyOrderByPdayDesc(company, pageable); }
	 
} 
