package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.Estimation;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.EstRepository;

@Service
public class EstServiceImpl implements EstService {
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	CompanyRepository companyRepo;

	@Override
	public Estimation getEstDetail(String id) {
		Estimation resultData = estRepo.getEstDetail(id);
		System.out.println("resultData는"+resultData);
		return resultData;
	}

	@Override
	public List<Estimation> getCEst(String cid) {
		Company com = companyRepo.findById(cid).get();
		return estRepo.findByCompany(com);
	}
	
	

	
	
}
