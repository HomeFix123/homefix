package com.homefix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.Company;
import com.homefix.domain.Member;
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
	public String moveDashBoardPage(Model model) {
		logger.info("관리자 대시보드");
		model.addAttribute("aggNewUser", adminService.aggregateNewUser());
		model.addAttribute("aggPayments", adminService.aggregatePayments());
		return "/admin/dashboard";
	}
	
	@GetMapping(path = "/member")
	public String moveMemberPage(Model model) {
		logger.info("관리자 고객관리");
		model.addAttribute("memberList", adminService.getMemberList());
		model.addAttribute("reportList", adminService.getMemberReportList());
		model.addAttribute("todayMemberReport", adminService.countTodayMemberReport());
		return "/admin/member";
	}
	
	@GetMapping(path = "/member/form/{id}")
	public String moveMemberUpdatePage(@PathVariable String id, Model model) {
		logger.info("관리자 고객관리 수정");
		model.addAttribute("member", adminService.getMember(id));
		return "/admin/member/form";
	}
	
	@PutMapping(path = "/member/form/{id}")
	public String updatePage(Member member, Model model) {
		logger.info("[member][" + member.getId() + "]관리자 고객관리 수정");
		adminService.updateMember(member);
		return "redirect:/admin/member";
	}
	
	
	@GetMapping(path = "/company")
	public String moveCompanyPage(Model model) {
		model.addAttribute("companyList", adminService.getCompanyList());
		model.addAttribute("reportList", adminService.getCompanyReportList());
		logger.info("관리자 업체관리");
		return "/admin/company";
	}
	
	
	@GetMapping(path = "/company/form/{cid}")
	public String moveCompanyUpdatePage(@PathVariable String cid, Model model) {
		logger.info("[company]["+ cid + "] 업체 수정");
		model.addAttribute("company", adminService.getCompany(cid));
		return "/admin/company/form";
	}
	
	@PutMapping(path = "/company/form/{cid}")
	public String updateCompany(Company company, Model model) {
		logger.info("[company]["+ company.getId() + "] 업체 수정");
		adminService.updateCompany(company);
		return "redirect:/admin/company";
	}
	
	@GetMapping(path = "/board")
	public String moveBoardPage() {
		
		return "redirect:/admin/board/brag";
	}
	
	
	@GetMapping(path = "/board/brag")
	public String moveBragPage(Integer page, Model model) {
		if(page == null) page = 1;
		model.addAttribute("bragList", adminService.getBragList(page)); 
		model.addAttribute("cntBrag", adminService.countBragList());
		return "/admin/board/brag";
	}
	
	
	
	@GetMapping(path = "/board/tip")
	public String moveTipPage(Integer page, Model model) {
		if(page == null) page = 1;
		model.addAttribute("tipList", adminService.getTipList(page)); 
		model.addAttribute("cntTip", adminService.countTipList());
		return "/admin/board/tip";
	}
	
	
	@GetMapping(path = "/chart")
	public String moveChartPage() {
		
		return "/admin/chart";
	}
}
