package com.homefix.persistence;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String>{
	
	public List<Payment> findByCompany(Company company);
	public List<Payment> findByCompany(Company company,Pageable pageable);
	
	
	public List<Payment> findByCompanyOrderByCompanyDesc(Company company);
	
	public List<Payment> findByCompanyOrderByPdayDesc(Company company,Pageable pageable);

}
