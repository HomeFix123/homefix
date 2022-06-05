package com.homefix.service;

import java.util.List;

import com.homefix.domain.ElasticCompany;

public interface ElasticCompanyService {
	
	public List<ElasticCompany> getCompanyByKeyword(String userId, String keyword, String area, String sort, Integer page);
}
