package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Estimation;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.EstRepository;

@Service
public class EstServiceImpl implements EstService {
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	CompanyRepository companyRepo;

	
	
}
