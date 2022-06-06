package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.Member;

public interface CompanyPreferRepository extends CrudRepository<CompanyPrefer, Integer> {
	
	List<CompanyPrefer> findByMember(Member member);
}
