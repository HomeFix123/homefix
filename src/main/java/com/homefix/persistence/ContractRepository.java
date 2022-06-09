package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Contract;
import com.homefix.domain.Estimation;


public interface ContractRepository extends CrudRepository<Contract, Integer>{

		List<Contract> findByCompany(Company company);
		
		Page<Contract> findByCompany(Company company, Pageable pageable);
		
		Contract findByEstimation(Estimation estimation);
		
		Contract findByCompanyAndEstimation(Company company,Estimation estimation);

		Page<Contract> findByCompanyAndIng(Company company, String ing, Pageable pageable);

}
