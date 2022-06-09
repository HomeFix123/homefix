package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;
import com.homefix.domain.Tip;

public interface TipRepository extends CrudRepository<Tip, Integer>{
	
	public List<Tip> findAll(Pageable pageabe);


	//개인 마이페이지에 팁 글 목록 불러오기용
	public List<Tip> findByMember(Member mem);

	
	public Tip findByTid(Integer tid );

