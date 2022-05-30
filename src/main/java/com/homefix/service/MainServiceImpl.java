package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.persistence.BragRepository;

@Service
public class MainServiceImpl implements MainService{
	@Autowired
	BragRepository bragRepo;
	
	public List<Brag> getBragList(){
		Pageable pageable = PageRequest.of(0, 4, Sort.by("bid").descending());
		return bragRepo.findAll(pageable);
	}
}
