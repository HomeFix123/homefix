package com.homefix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.Brag;
import com.homefix.service.BragService;

@Controller
@RequestMapping("/brag")
public class BragController {
	
	static final Logger logger = LoggerFactory.getLogger(BragController.class);
	
	@Autowired
	private BragService bragService;
	
	@GetMapping("/write")
	public String insertReview() {
		return "/brag/BragWrite";
	}
	
	@PostMapping("/write")
	public String saveBrag(Brag brag, String cid) {
		String id = "test";
		bragService.saveBrag(brag, cid, id);
		logger.info("입력성공");
		return "redirect:/brag/";
	}
	
	
	
	
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
}
