package com.homefix.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;

public interface MemberReportRepository extends CrudRepository<MemberReport, Integer>{
	public long countByRdayGreaterThan(Date rday);
	
	public List<MemberReport> findByMember(Member member);

	public List<MemberReport> findByMemberAndReporter(Member member, Member repo);
}
