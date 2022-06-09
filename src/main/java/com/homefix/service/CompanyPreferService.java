package com.homefix.service;

import org.springframework.data.domain.Page;

import com.homefix.domain.CompanyPrefer;

public interface CompanyPreferService {

	//마이페이지 좋아요찍은 업체 목록 페이징
	Page<CompanyPrefer> getLoveComList(String id, Integer page);

	public long countLoveComList(String id);

}
