package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Member;

public interface BragRepository extends CrudRepository<Brag, String>{
	public List<Brag> findAll(Pageable pageable);
	
	public Brag findByBid(Integer bid);

	public Brag findByBidAndMember(Integer bid, Member member);
	
	public List<Brag> findByCompany(Company com);

	//개인마이페이지 후기
	public List<Brag> findByMember(Member member);
}
