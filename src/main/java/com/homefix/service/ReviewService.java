package com.homefix.service;

import java.util.List;

import com.homefix.domain.Company;
import com.homefix.domain.ElasticReview;
import com.homefix.domain.Review;

public interface ReviewService {
	
	// 시공 후기 작성하기
	public void saveReview(Review rev, String cid);
	

	// 시공 후기 상세
	public Review getReview(Review rev);
	
	// 시공 후기 삭제
	public void deleteReview(Integer rid, String cid);
	
	// 신고하기
	public String saveCReport(Company cid, String id, String reason);
	
	// 시공 후기 리스트
	public List<ElasticReview> getReviewList(Integer page, String hometype, String job, String family);
}
