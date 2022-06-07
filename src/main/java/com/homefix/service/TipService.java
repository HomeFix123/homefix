package com.homefix.service;

import java.util.List;
import com.homefix.domain.Tip;


public interface TipService {
	
	// 팁 목록 보기, 페이징 처리
	List<Tip> getTipList(Tip tip, int page);
	
	// 팁 게시글 개수 불러오기
	public long countEstList(); //페이징 처리
	
	// 팁 작성글 입력하기
	public void saveTip(Tip tip, String id);
	
	// 좋아요 입력
	public void savePrefer(Tip tip, String id);
		
	// 좋아요 취소
	public void deletePrefer(Tip tip, String id);
		
	
	
	

}
