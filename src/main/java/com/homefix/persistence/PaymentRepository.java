package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String>{
	
	
	
}
