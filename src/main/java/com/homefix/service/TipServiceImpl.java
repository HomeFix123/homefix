package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Tip;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.TipRepository;

@Service
public class TipServiceImpl implements TipService{
	
	@Autowired
	TipRepository tipRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	// 팁 전체 목록
	@Override
	public List<Tip> getTipList(Tip tip, int page) {
		//return (List<Tip>)tipRepo.findAll();
		int showCntPerPage = 5;
		Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("tid").descending());
		return tipRepo.findAll(pageable);
	}
	
	// 팁 전체 개수 (페이징 용)
	@Override
	public long countEstList() {
		int showCntPerPage = 5;
		return (long)(tipRepo.count()+1)/showCntPerPage + 1;
	}
	
	
	// 팁 작성글 입력
	@Override
	public void saveTip(Tip tip, String id) {
		tip.setMember(memberRepo.findById(id).get());
		tipRepo.save(tip);
		System.out.println("입력값 확인 " + tipRepo.save(tip));
		
	}
	

}
