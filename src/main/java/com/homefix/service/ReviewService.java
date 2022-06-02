package com.homefix.service;

import java.util.List;

import com.homefix.domain.Brag;
import com.homefix.domain.Review;

public interface ReviewService {
	
	// 인테리어 자랑(후기) 작성하기
	public void saveReview(Review rev, String cid);
	
	// 인테리어 자랑 리스트
	public List<Review> getReviewList(Review rev);
	
}
