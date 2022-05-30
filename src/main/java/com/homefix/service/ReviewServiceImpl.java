package com.homefix.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.domain.Review;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	/*
	 * @Autowired MemberRepository memberRepo;
	 */
	
	@Autowired
	CompanyRepository companyRepo;
	
	
	@Override
	public void saveReview(Review rev, String cid) {
		
		rev.setRcnt(0);
		rev.setCompany(companyRepo.findById(cid).get());
		rev.setRdate(new Date());
		
		System.out.println(rev);
		reviewRepo.save(rev);
		
	}
	
	@Override
	public List<Review> getReviewList(Review rev) {
		
		return (List<Review>)reviewRepo.findAll();
	}
	
	
}
