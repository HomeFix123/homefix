package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.domain.ElasticReview;
import com.homefix.domain.Review;
import com.homefix.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
	private ReviewService reviewService;
	
	// 시공후기 작성 페이지
	@GetMapping("/write")
	public String insertReview() {		
		return "review/ReviewWrite";
	}
	
	// 시공후기 작성
	@PostMapping("/write")
	public String saveReview(HttpSession session,Review rev) {
		String cid = (String)session.getAttribute("userId");
		if(cid == null) {
			return "redirect:/index";
		}
		reviewService.saveReview(rev, cid);
		logger.info("입력성공");
		return "redirect:/review";
	}
	
	// 시공후기 목록
	@GetMapping
	public String getReviewList(Model m, String hometype, String job, String family){
		
		m.addAttribute("reviewList", reviewService.getReviewList(1, hometype, job, family));
		return "review/ReviewList";
	}
	
	// 시공후기 엘라스틱 페이징
	@GetMapping("/page")
	@ResponseBody
	public List<ElasticReview> getReviewListPerPage(Model m, Integer page, String hometype, String job, String family){

		return reviewService.getReviewList(page, hometype, job, family);
	}
	
	// 시공후기 상세
	@GetMapping("/{rid}")
	public String getReview(Model m, Review rev) {
		Review result = reviewService.getReview(rev);
		m.addAttribute("rev", result);
		return "review/ReviewDetail";
	}
	
	// 시공후기 삭제하기
	@DeleteMapping("/{rid}")
	public String deleteReview(Review rev, HttpSession session) {
		String cid = (String) session.getAttribute("userId");
		reviewService.deleteReview(rev, cid);
		return "redirect:/review";
	}
	
	// 시공후기 업체 신고 하기
	@PostMapping("/report/{cid}")
	@ResponseBody
	public String saveCReport(@PathVariable(name = "cid") Company cid, String reason, HttpSession session) {
		String id = (String) session.getAttribute("memberId");
		
		// 로그인 여부 확인
		if(id == null) {
			return "notLogin";
		}
		
		return reviewService.saveCReport(cid, id, reason);
	}
	
}
