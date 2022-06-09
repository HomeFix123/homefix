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

	
	
	//사업자 결제정보 DB저장
	public void paymentInfoInsert(Payment vo) {
		repo.save(vo);
	};

	
	
	
	// 사업자 결제정보(마지막 구독일) 조회
	public List<Payment> RemainingDate(Company company) {
		return repo.findByCompanyOrderByPday(company);
	}

	
	
	//사업자 구독관리(결제내역)페이징 처리
	public List<Payment> RemainDate(Company company, Integer page) {
		int showCntPerPage = 6;
		Pageable pageable = (Pageable) PageRequest.of(page - 1, showCntPerPage, Sort.by("pday").descending());
		return repo.findByCompany(company, pageable);
	}

}
