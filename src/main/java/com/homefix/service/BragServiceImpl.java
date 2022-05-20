package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class BragServiceImpl implements BragService {
	
	@Autowired
	BragRepository bragRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	
	@Override
	public void saveBrag(Brag brag, String id, String cid) {
		
		brag.setMember(memberRepo.findById(id).get());
		brag.setCompany(companyRepo.findById(cid).get());
		
		
		bragRepo.save(brag);
		
	}
	
	
}
