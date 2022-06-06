package com.homefix.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.CompanyArea;
import com.homefix.domain.CompanyInfo;

public interface CompanyAreaRepository extends CrudRepository<CompanyArea, Integer>{
	@Transactional
	void deleteAllByCompanyInfo(CompanyInfo companyInfo);
}
