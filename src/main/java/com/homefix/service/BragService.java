package com.homefix.service;

import com.homefix.domain.Brag;

public interface BragService {
	
	// 인테리어 자랑(후기) 작성하기
	public void saveBrag(Brag brag, String id, String cid);
	
}
