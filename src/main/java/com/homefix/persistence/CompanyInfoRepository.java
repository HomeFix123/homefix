package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;

public interface CompanyInfoRepository extends CrudRepository<CompanyInfo, Integer>{
	
	List<CompanyInfo> findByCompany(Company company);
}
