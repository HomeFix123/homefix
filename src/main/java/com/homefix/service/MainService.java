package com.homefix.service;

import java.util.List;

import com.homefix.domain.Brag;
import com.homefix.domain.Review;

public interface MainService {
	public List<Brag> getBragList();
	
	public List<Review> getReviewList();
	
	
}
