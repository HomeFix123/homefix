package com.homefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homefix.domain.CompanyInfo;
import com.homefix.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping(path = "/company/{cid}")
	public CompanyInfo showCompanyDetail(@PathVariable String cid) {
		if(cid == null) {
			return null;
		}
		
		return adminService.getCompanyDetail(cid);
	}
}
