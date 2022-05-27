package com.homefix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;
import com.homefix.domain.Payment;
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
	
	/*
	 * 업체 블랙리스트 등록/해제
	 */
	@PutMapping(path = "/company/blacklist/{cid}")
	public void enableBlacklist(@PathVariable String cid, Boolean enabled) {
		adminService.enableBlacklist(cid, enabled);
	}
	
	/*
	 * 고객 상세정보
	 */
	@GetMapping(path = "/member/{id}")
	public Member getMember(@PathVariable String id) {
		if(id == null) {
			return null;
		}
		
		return adminService.getMember(id);
	}
	
	/*
	 * 고객 블랙리스트 등록/해제
	 */
	@PutMapping(path = "/member/blacklist/{id}")
	public void enableBlackMember(@PathVariable String id, Boolean enabled) {
		adminService.enableBlackMember(id, enabled);
	}
	
	/*
	 * 고객 신고 삭제
	 */
	@DeleteMapping(path = "/member/report/{rid}")
	public void deleteMemberReport(@PathVariable String rid) {
		if(rid == null) {
			return;
		}
		adminService.deleteMemberReport(rid);
	}
	
	/*
	 * 업체 신고 삭제
	 */
	@DeleteMapping(path = "/company/report/{rid}")
	public void deleteCompanyReport(@PathVariable String rid) {
		if(rid == null) {
			return;
		}
		adminService.deleteCompanyReport(rid);
	}
	
	/*
	 * 업체 결제내역
	 */
	@GetMapping(path = "/company/payment/{cid}")
	public List<Payment> getPaymentList(@PathVariable String cid){
		if(cid == null) {
			return null;
		}
		return adminService.getPaymentList(cid);
	}
	
	/*
	 * 고객 신고내역
	 */
	@GetMapping(path = "/member/report/{id}")
	public List<MemberReport> getMemberReportById(@PathVariable String id){
		if(id == null){
			return null;
		}
		
		return adminService.getMemberReport(id);
	}
}
