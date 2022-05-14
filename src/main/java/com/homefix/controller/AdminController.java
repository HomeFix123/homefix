package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping(path = "")
	public String moveAdminPage() {
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping(path = "/dashboard")
	public String moveDashBoardPage() {
		
		return "/admin/dashboard";
	}
	
	@GetMapping(path = "/member")
	public String moveMemberPage() {
		
		return "/admin/member";
	}
	
	@GetMapping(path = "/member/form")
	public String moveMemberUpdatePage() {
		
		return "/admin/member/form";
	}
	
	
	
	@GetMapping(path = "/company")
	public String moveCompanyPage() {
		
		return "/admin/company";
	}
	
	@GetMapping(path = "/company/form")
	public String moveCompanyUpdatePage() {
		return "/admin/company/form";
	}
	
	
	@GetMapping(path = "/board")
	public String moveBoardPage() {
		
		return "/admin/board";
	}
	
	@GetMapping(path = "/chart")
	public String moveChartPage() {
		
		return "/admin/chart";
	}
}
