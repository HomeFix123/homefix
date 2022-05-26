package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String>{
	
	public List<Payment> findByCompany(Company company);
	
}
