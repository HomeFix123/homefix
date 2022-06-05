package com.homefix.controller;

import java.util.List;
import java.util.Set;

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
import com.homefix.domain.Contract;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.service.BragService;

@Controller
@RequestMapping("/brag")
public class BragController {
	
	static final Logger logger = LoggerFactory.getLogger(BragController.class);
	
	@Autowired
	private BragService bragService;
	
	@GetMapping("/write")
	public String insertBrag(Model m) {
		String id = "test";
		Set<Company> list = bragService.getContractList(id);
		m.addAttribute("contract", list);
		return "brag/BragWrite";
	}
	
	@PostMapping("/write")
	public String saveBrag(Brag brag, String cid) {
		System.out.println(cid);
		String id = "test";
		bragService.saveBrag(brag, cid, id);
		logger.info("입력성공");
		return "redirect:/brag";
	}
	
	@GetMapping
	public String getBragList(Model m){
		
		Brag brag = new Brag();
		List<Brag> list = bragService.getBragList(brag);
		m.addAttribute("bragList", list);
		return "brag/BragList";
	}
	
	
	@GetMapping("/{bid}")
	public String getBrag(Model m, Brag brag) {
		String id = "test";
		Brag result = bragService.getBrag(brag, id);
		m.addAttribute("brag", result);
		return "brag/BragDetail";
	}
	
	@DeleteMapping("/{bid}")
	public String deleteBrag(Brag brag) {
		String id = "test";
		bragService.deleteBrag(brag, id);
		return "redirect:/brag";
	}
	
	
	
	@PostMapping("/prefer/{bid}")
	@ResponseBody
	public void savePrefer(Brag brag) { 
		String id = "test"; 
		bragService.savePrefer(brag, id);
		  
	}
	  
	@DeleteMapping("/prefer/{bid}")
	@ResponseBody
	public void deletePrefer(Brag brag) { 
		String id = "test"; 
		bragService.deletePrefer(brag, id); 
	  
	}
	
	@PostMapping("/report/{id}")
	@ResponseBody
	public String saveReport(Member id, String reason) {
		String reporter = "test123";
		System.out.println("id: "+ id + " 사유: "+ reason);
		return bragService.saveReport(id, reporter, reason);
	}
	
}
