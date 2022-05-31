package com.homefix.service;

import java.util.List;

import com.homefix.domain.Tip;

public interface TipService {
	
	// 팁 목록 보기
	List<Tip> tipList(Tip tip);
		
	// 팁 작성글 입력하기
	public void saveTip(Tip tip, String id);
		

}
