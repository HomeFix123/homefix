package com.homefix.persistence;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanySpecial;

public interface CompanySpecialRepository extends CrudRepository<CompanySpecial, Integer>{
	@Transactional
	void deleteAllByCompanyInfo(CompanyInfo companyInfo);
}
