package com.homefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.homefix.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@GetMapping(name = "/")
	public String mainPage(Model model) {
		model.addAttribute("bragList", mainService.getBragList());
		model.addAttribute("reviewList", mainService.getReviewList());
		return "index";
	}
	
	@GetMapping("/{step}")
	public String viewPage(@PathVariable String step) {
		
		return "redirect:/";
	}
}
