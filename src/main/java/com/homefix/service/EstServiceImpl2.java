package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.homefix.persistence.EstRepository2;

@Service
public class EstServiceImpl2 implements EstService2 {
	
	@Autowired
	EstRepository2 estRepo;
	
}
