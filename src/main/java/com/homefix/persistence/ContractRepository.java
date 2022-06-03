package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Contract;
import com.homefix.domain.Estimation;


public interface ContractRepository extends CrudRepository<Contract, Integer>{

	Contract findByEstimation(Estimation estimation);

	
		
}
