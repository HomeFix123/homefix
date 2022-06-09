package com.homefix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public String mainPage(HttpSession session,Model model) {
		model.addAttribute("bragList", mainService.getBragList());
		model.addAttribute("reviewList", mainService.getReviewList());
		model.addAttribute("session", session);
		return "index";
	}

	@GetMapping("/{step}")
	public String viewPage(@PathVariable String step) {

		return "redirect:/";
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index";
	}
}
