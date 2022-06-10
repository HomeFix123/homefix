package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.Member;

public interface CompanyPreferRepository extends CrudRepository<CompanyPrefer, Integer> {
	
	List<CompanyPrefer> findByMember(Member member);

	Page<CompanyPrefer> findByMember(Member mem, Pageable pageable);

	int countByMember(Member member);

	CompanyPrefer findByMemberAndCompany(Member mem, Company co);
}
