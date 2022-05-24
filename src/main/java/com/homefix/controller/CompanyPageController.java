package com.homefix.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.CompanyDto;

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
