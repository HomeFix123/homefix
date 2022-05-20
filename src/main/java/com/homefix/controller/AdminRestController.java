package com.homefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Member;
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
	
	@PostMapping(path = "/company/blacklist/{cid}")
	public void enableBlacklist(@PathVariable String cid, Boolean enabled) {
		adminService.enableBlacklist(cid, enabled);
	}
	
	@GetMapping(path = "/member/{id}")
	public Member getMember(@PathVariable String id) {
		if(id == null) {
			return null;
		}
		
		return adminService.getMember(id);
	}
	
	@PostMapping(path = "/member/blacklist/{id}")
	public void enableBlackMember(@PathVariable String id, Boolean enabled) {
		adminService.enableBlackMember(id, enabled);
	}
}
