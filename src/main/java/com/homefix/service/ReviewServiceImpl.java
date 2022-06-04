package com.homefix.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.Member;
import com.homefix.domain.Review;
import com.homefix.persistence.CompanyReportRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	
	@Autowired 
	MemberRepository memberRepo;
	 
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	CompanyReportRepository companyRRepo;
	
	
	
	
	
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
	
	@Override
	public Review getReview(Review rev ) {
		
		return reviewRepo.findByRid(rev.getRid());
		
	}
	
	@Override
	public void deleteReview(Review rev, String cid) {
		Review result = reviewRepo.findByRid(rev.getRid());
		result.setCompany(companyRepo.findById(cid).get());
		reviewRepo.delete(reviewRepo.findByRidAndCompany(result.getRid(), result.getCompany()));
	}
	
	
	@Override
	public String saveCReport(Company cid, String id, String reason) {
		Member repo = memberRepo.findById(id).get();
		List<CompanyReport> list = companyRRepo.findByCompanyAndMember(id, repo);
		if(list.isEmpty()) {
			CompanyReport result = new CompanyReport();
			result.setCompany(cid);
			result.setMember(repo);
			result.setReason(reason);
			companyRRepo.save(result);
			return "true";
			
		} else {
			
			return "false";
		
		}
	}
	
	
	
	
}
