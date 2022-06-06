package com.homefix.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Review;
import com.homefix.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/write")
	public String insertReview() {		
		return "review/ReviewWrite";
	}
	
	@PostMapping("/write")
	public String saveReview(Review rev) {
		
		String cid = "1004";
		reviewService.saveReview(rev, cid);
		logger.info("입력성공");
		return "redirect:/review/write";
	}
	
	@GetMapping
	public String getReviewList(Model m){
		
		Review rev = new Review();
		List<Review> list = reviewService.getReviewList(rev);
		m.addAttribute("reviewList", list);
		return "review/ReviewList";
	}
	
	
	
	@GetMapping("/{rid}")
	public String getReview(Model m, Review rev) {
		Review result = reviewService.getReview(rev);
		m.addAttribute("rev", result);
		return "review/ReviewDetail";
	}
	
	@DeleteMapping("/{rid}")
	public String deleteReview(Review rev) {
		String cid = "1004";
		reviewService.deleteReview(rev, cid);
		return "redirect:/review";
	}
	
	
	@PostMapping("/Creport/{cid}")
	@ResponseBody
	public String saveCReport(Company cid, String reason) {
		String id = "test";
		System.out.println("cid: "+ cid + " 사유: "+ reason);
		return reviewService.saveCReport(cid, id, reason);
	}
	
}
