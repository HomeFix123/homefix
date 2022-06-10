package com.homefix.controller;

import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

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

import com.homefix.domain.Tip;
import com.homefix.service.TipService;

@Controller
@RequestMapping("/tip")  // 뷰폴더 이름이 아니라 요청하는 url 주소
public class TipController {
	/*
	 * @GetMapping("/{step}") public void viewPage(@PathVariable String step) {
	 * //return "/board/" + step; }
	 */
	
	static final Logger logger = LoggerFactory.getLogger(TipController.class);
	
	@Autowired
	private TipService tipService;
	
	/* 팁 전체 목록 페이지 */
	@GetMapping("/list") 
	public String tipList(Model m, Integer page, HttpSession session) {
		if(page == null) page = 1;
		String id = (String)session.getAttribute("memberId");
		String cid = (String)session.getAttribute("userId");
		if ( id== null && cid == null) {
			return "redirect:/sign"; // 멤버 아이디가 없으면 로그인 페이지로 이동
		} 
		Tip tip = new Tip();
		//List<Tip> list = tipService.getTipList(tip);
		m.addAttribute("cntTip", tipService.countEstList()); //페이징
		m.addAttribute("tipList", tipService.getTipList(tip, page, id));
		return "tip/interiortip";
	}
	
	/* 팁 글작성 페이지 */
	@GetMapping("/write")
	public String insertTip() {
		return "tip/interiortip-input";
	}
	
	@PostMapping("/write")
	public String saveTip(Tip tip, HttpSession session) {
		String id = (String)session.getAttribute("memberId"); // 나중에는 세션에서 ID 값을 가져옴, 현재 테스트로 ID 직접 넣어줌
		tipService.saveTip(tip, id);
		logger.info("입력성공");
		return "redirect:/tip/list";
	}
	
	
	// 좋아요 입력
	@PostMapping("/prefer/{tid}")
	@ResponseBody
	public void savePrefer(Tip tip, HttpSession session) { 
		String id = (String)session.getAttribute("memberId");
		tipService.savePrefer(tip, id);
		System.out.println("좋아요 성공 컨트롤러 옴");  
	}
	
	// 좋아요 취소
	@DeleteMapping("/prefer/{tid}")
	@ResponseBody
	public void deletePrefer(Tip tip, HttpSession session) { 
		String id = (String)session.getAttribute("memberId");
		tipService.deletePrefer(tip, id); 
		System.out.println("좋아요 취소 컨트롤러 옴");
	}
	
	
}
