package com.homefix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	/*
	 * 업체 상세정보
	 */
	@GetMapping(path = "/company/{cid}")
	public CompanyInfo showCompanyDetail(@PathVariable String cid) {
		if(cid == null) {
			return null;
		}
		
		return adminService.getCompanyDetail(cid);
	}
	
	@PutMapping(path = "/company/blacklist/{cid}")
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
	
	@PutMapping(path = "/member/blacklist/{id}")
	public void enableBlackMember(@PathVariable String id, Boolean enabled) {
		adminService.enableBlackMember(id, enabled);
	}
	
	@DeleteMapping(path = "/company/report/{rid}")
	public void deleteReport(@PathVariable String rid) {
		if(rid == null) {
			return;
		}
		adminService.deleteReport(rid);
	}
}
