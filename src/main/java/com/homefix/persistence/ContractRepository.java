package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Contract;


public interface ContractRepository extends CrudRepository<Contract, Integer>{
		List<Contract> findByCompay(Company compay);
}
