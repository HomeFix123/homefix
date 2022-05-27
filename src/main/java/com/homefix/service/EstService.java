package com.homefix.service;

import java.util.List;

import com.homefix.domain.Estimation;
import com.homefix.domain.Member;

public interface EstService {
	
	//견적 상세목록 불러오기
	public Estimation getEstDetail(String id);
	
	public List<Estimation> getCEst(String id);
}
