package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Tip> tipList(Tip tip) {
		return (List<Tip>)tipRepo.findAll();
	}
	
	// 팁 작성글 입력
	@Override
	public void saveTip(Tip tip, String id) {
		tip.setMember(memberRepo.findById(id).get());
		tipRepo.save(tip);
		System.out.println("입력값 확인 " + tipRepo.save(tip));
		
	}
	

}
