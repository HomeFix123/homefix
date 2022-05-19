package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.persistence.BragRepository;

@Service
public class BragServiceImpl implements BragService {
	
	@Autowired
	BragRepository bragRepo;
	
	
	@Override
	public void saveBrag(Brag brag) {
		
		Brag temp = new Brag();
		
		
		bragRepo.save(temp);
		
	}
	
	
}
