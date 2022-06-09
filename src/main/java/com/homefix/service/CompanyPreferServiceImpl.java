package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.Member;
import com.homefix.persistence.CompanyPreferRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class CompanyPreferServiceImpl implements CompanyPreferService{
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyPreferRepository coPrRepo;

	//개인 마이페이지 좋아요 찍은 업체 목록페이지 
	@Override
	public Page<CompanyPrefer> getLoveComList(String id, Integer page) {
		int showCntPerPage = 3;	//한번에 보여줄 게시물의 수
		Member mem = memberRepo.findById(id).get();
		Pageable pageable = PageRequest.of(page, showCntPerPage,Sort.by("id").descending());
		return coPrRepo.findByMember(mem,pageable);
	}

	@Override
	public long countLoveComList(String id) {
		int showCntPerPage = 10;
		Member member = memberRepo.findById(id).get();
		return (long)(coPrRepo.countByMember(member)-1)/showCntPerPage + 1;
	}

}
