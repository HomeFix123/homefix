package com.homefix.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.ElasticBrag;
import com.homefix.domain.Member;
import com.homefix.service.BragService;

@Controller
@RequestMapping("/brag")
public class BragController {
	
	static final Logger logger = LoggerFactory.getLogger(BragController.class);
	
	@Autowired
	private BragService bragService;
	
	@GetMapping("/write")
	public String insertBrag(Model m, HttpSession session) {
		String id = (String) session.getAttribute("memberId");
		Set<Company> list = bragService.getContractList(id);
		m.addAttribute("contract", list);
		return "brag/BragWrite";
	}
	
	@PostMapping("/write")
	public String saveBrag(Brag brag, String cid, HttpSession session) {
		System.out.println(cid);
		String id = (String) session.getAttribute("memberId");
		bragService.saveBrag(brag, cid, id);
		logger.info("입력성공");
		return "redirect:/brag";
	}
	
	@GetMapping
	public String getBragList(Model m, HttpSession session, String sort, String loc, 
			String family, String hometype){
		
		String id = (String) session.getAttribute("memberId");
		m.addAttribute("bragList", bragService.getBragByKeyword(id, loc, family, hometype, sort, 1));
		
		return "brag/BragList";
	}
	
	@GetMapping("/page")
	@ResponseBody
	public List<ElasticBrag> getBragList(HttpSession session, String sort, String loc, 
			String family, String hometype, Integer page){
		String id = (String) session.getAttribute("memberId");
		return bragService.getBragByKeyword(id, loc, family, hometype, sort, page);
	}
	
	
	@GetMapping("/{bid}")
	public String getBrag(Model m, Brag brag, HttpSession session) {
		String id = (String) session.getAttribute("memberId");
		Brag result = bragService.getBrag(brag, id);
		m.addAttribute("brag", result);
		return "brag/BragDetail";
	}
	
	@DeleteMapping("/{bid}")
	public String deleteBrag(Brag brag, HttpSession session) {
		String id = (String) session.getAttribute("memberId");
		bragService.deleteBrag(brag, id);
		return "redirect:/brag";
	}
	
	
	
	@PostMapping("/prefer/{bid}")
	@ResponseBody
	public void savePrefer(Brag brag, HttpSession session) { 
		String id = (String) session.getAttribute("memberId");
		bragService.savePrefer(brag, id);
		  
	}
	  
	@DeleteMapping("/prefer/{bid}")
	@ResponseBody
	public void deletePrefer(Brag brag, HttpSession session) { 
		String id = (String) session.getAttribute("memberId");
		bragService.deletePrefer(brag, id); 
	  
	}
	
	@PostMapping("/report/{id}")
	@ResponseBody
	public String saveReport(Member id, String reason, HttpSession session) {
		String reporter = (String) session.getAttribute("memberId");
		System.out.println("id: "+ id + " 사유: "+ reason);
		return bragService.saveReport(id, reporter, reason);
	}
	
}
