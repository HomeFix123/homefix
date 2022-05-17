package com.homefix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	AdminService adminService;
	
	@GetMapping(path = "")
	public String moveAdminPage() {
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping(path = "/dashboard")
	public String moveDashBoardPage() {
		logger.info("관리자 대시보드");
		return "/admin/dashboard";
	}
	
	@GetMapping(path = "/member")
	public String moveMemberPage() {
		logger.info("관리자 고객관리");
		return "/admin/member";
	}
	
	@GetMapping(path = "/member/form")
	public String moveMemberUpdatePage() {
		logger.info("관리자 고객관리 수정");
		return "/admin/member/form";
	}
	
	
	@GetMapping(path = "/company")
	public String moveCompanyPage(Model model) {
		model.addAttribute("companyList", adminService.getCompanyList());
		logger.info("관리자 업체관리");
		return "/admin/company";
	}
	
	
	@GetMapping(path = "/company/form/{cid}")
	public String moveCompanyUpdatePage(@PathVariable String cid) {
		logger.info("[company]["+ cid + "] 업체 수정");
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
