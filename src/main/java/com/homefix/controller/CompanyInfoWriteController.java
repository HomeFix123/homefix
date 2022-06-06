package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.CompanyInfo;
import com.homefix.service.CompanyWriteService;

@Controller
@RequestMapping("/company")
public class CompanyInfoWriteController {
	
	@Autowired
	CompanyWriteService companyWriteService;
	
	@GetMapping("/write")
	public String moveCompanyInfo(Model model, HttpSession session) {
		String companyId = (String)session.getAttribute("userId");
		if(companyId == null) {
			return "redirect:/index";
		}
		model.addAttribute("companyInfo", companyWriteService.getCompanyInfo(companyId));
		List<String> areaNameList = companyWriteService.getAreaNameList();
		model.addAttribute("areaNameList", areaNameList);
		model.addAttribute("areaList", companyWriteService.getAreaList(areaNameList));
		
		
		
		return "company/write";
	}
	@PostMapping("/write")
	public String writeCompanyInfo(HttpSession session, String[] specialtyArr, String[] spacesArr, CompanyInfo companyInfo) {
		String companyId = (String)session.getAttribute("userId");
		companyWriteService.insertCompanyInfo(companyId, companyInfo, specialtyArr, spacesArr);
		
		
		return "redirect:/company/write";
	}
}
