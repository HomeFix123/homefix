package com.homefix.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}
