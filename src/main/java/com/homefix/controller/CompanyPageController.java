package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyPageController {

	@GetMapping("/companyprofile")
	public void companyMypage(){
		
	}
	
	@GetMapping("/companydetails")
	public void companyDetailPage() {
		
	}
	
}
