package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.Member;

public interface CompanyReportRepository extends CrudRepository<CompanyReport, Integer> {
	public long countByCompany(Company company);
	
	public List<CompanyReport> findByCompany(Company company);

	public List<CompanyReport> findByCompanyAndMember(String id, Member repo);
	
}
