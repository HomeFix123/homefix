package com.homefix.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyPageController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	CompanyService companyService;
	
	
	//사업체 마이페이지-정보수정페이지 정보 불러오기
	@GetMapping("/companyprofile")
	public void getCompanyMypage(HttpSession session, Model model){
		String id= session.getAttribute("userId").toString();
		model.addAttribute("company",companyService.getCompanyMyInfo(id));
	}
	
	 
	//사업체 상세페이지 로딩
	@GetMapping("/companydetails")
	public void companyDetailPage() {
		
	}
	
	
	
	
}
