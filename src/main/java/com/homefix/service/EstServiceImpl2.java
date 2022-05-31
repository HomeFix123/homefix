package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.homefix.domain.Estimation;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class EstServiceImpl2 implements EstService2 {
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyRepository companyRepo;

	@Override
	public void saveEst(Estimation est, String id) {
		est.setMember(memberRepo.findById(id).get());
		estRepo.save(est);
		System.out.println("입력값 확인 " + estRepo.save(est));
	}

	// 전체견적 리스트
	@Override
	public List<Estimation> estList(Estimation est) {
		return (List<Estimation>)estRepo.findAll();
		//Sort sort = Sort.by(Sort.Direction.DESC, "edate");
		//return (List<Estimation>)estRepo.findAllDesc();
		
	}
	
	// 전체 견적 목록 상세보기
	@Override
	public Estimation getEstDetails(String id) {
		System.out.println("서비스에서 가져온 아이디 " + estRepo.getEstDetail(id));
		return estRepo.getEstDetail(id); //EstRepository에 있는 getEstDetail함수 리턴
	}
	
	
	
	 
	
	
	
}
