package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Member;

public interface BragRepository extends CrudRepository<Brag, String>{
	
	public Brag findByBid(Integer bid);

	public Brag findByBidAndMember(Integer bid, Member member);
	
	public List<Brag> findByCompany(Company com);

	//개인마이페이지 후기
	public List<Brag> findByMember(Member member);

	//개인마이페이지 후기목록 페이징
	public List<Brag> findAll(Pageable pageable);
	
	public Page<Brag> findByMember(Member mem,Pageable pageable);
	
	public long countByMember(Member member);
	
	
	
}
