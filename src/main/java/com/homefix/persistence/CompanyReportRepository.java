package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyReport;

public interface CompanyReportRepository extends CrudRepository<CompanyReport, Integer> {
	public long countByCompany(Company company);
	
	public List<CompanyReport> findByCompany(Company company);
}
