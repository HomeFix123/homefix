package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Payment;
import com.homefix.persistence.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository repo;

	public void paymentInfoInsert(Payment vo) {
		repo.save(vo);

	};

}
