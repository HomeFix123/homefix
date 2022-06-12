package com.homefix.service;

import java.util.List;

import com.homefix.domain.Estimation;

public interface EstService2 {
	
	// 견적상담 신청서 입력하기
	public void saveEst(Estimation est, String id);
	
	// 전체 견적 목록
	List<Estimation> estList(Estimation est);
	
}
