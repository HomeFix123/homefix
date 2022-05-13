package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping(path = "/")
	public String moveAdminPage() {
		
		return "/admin/member";
	}
	
	@GetMapping(path = "/dashboard")
	public String moveDashBoardPage() {
		
		return "/admin/dashboard";
	}
	
	@GetMapping(path = "/member")
	public String moveMemberPage() {
		
		return "/admin/member";
	}
}
