package com.homefix.persistence;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.MemberReport;

public interface MemberReportRepository extends CrudRepository<MemberReport, Integer>{
	public long countByRdayGreaterThan(Date rday);
}
