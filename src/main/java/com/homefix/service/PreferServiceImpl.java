package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Member;
import com.homefix.domain.Prefer;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PreferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreferServiceImpl implements PreferService {
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	private PreferRepository loveRepo;
	
	//개인 마이페이지 후기목록 페이징 (1)
	@Override
	public Page<Prefer> getLoveList(String id,Integer page) {
		int showCntPerPage = 3;	//한번에 보여줄 게시물의 수
		Member mem = memberRepo.findById(id).get();
		Pageable pageable = PageRequest.of(page, showCntPerPage,Sort.by("lid").descending());
		return loveRepo.findByMember(mem,pageable);
	}
	
	//개인 마이페이지 후기목록 페이징 (2)
	@Override
	public long countLoveList(String id) {
		int showCntPerPage = 10;
		Member member = memberRepo.findById(id).get();
		return (long)(loveRepo.countByMember(member)-1)/showCntPerPage + 1;
	}

}
