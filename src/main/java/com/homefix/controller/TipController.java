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
	public String tipList(Model m) {
		
		Tip tip = new Tip();
		List<Tip> list = tipService.tipList(tip);
		m.addAttribute("tipList", list);
		return "tip/interiortip";
	}
	
	/* 팁 글작성 페이지 */
	@GetMapping("/write")
	public String insertTip() {
		return "tip/interiortip-input";
	}
	
	@PostMapping("/write")
	public String saveTip(Tip tip) {
		String id = "test"; // 나중에는 세션에서 ID 값을 가져옴, 현재 테스트로 ID 직접 넣어줌
		tipService.saveTip(tip, id);
		logger.info("입력성공");
		return "tip/list"; //redirect는 요청 url 주소를 써줌
	}
	
	
	
	
}
