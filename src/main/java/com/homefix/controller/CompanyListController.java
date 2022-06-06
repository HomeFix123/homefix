package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.ElasticCompany;
import com.homefix.service.ElasticCompanyService;

@Controller
@RequestMapping("expert")
public class CompanyListController {
	
	@Autowired
	private ElasticCompanyService elasticService;
	
	
	@GetMapping("")
	public String getCompanyList(Model model, HttpSession session, String keyword, String sort, String area) {
		String id = (String) session.getAttribute("memberId");
		model.addAttribute("companyList", elasticService.getCompanyByKeyword(id, keyword, area, sort, 1));
		
		
		return "brag/coList";
	}
	
	@GetMapping("/page")
	@ResponseBody
	public List<ElasticCompany> getCompanyList(HttpSession session, String keyword, String sort, String area, Integer page) {
		String id = (String) session.getAttribute("memberId");
		
		return elasticService.getCompanyByKeyword(id, keyword, area, sort, page);
	}
}
