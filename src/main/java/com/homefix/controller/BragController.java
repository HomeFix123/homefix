package com.homefix.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.Brag;
import com.homefix.service.BragService;

@Controller
@RequestMapping("/brag")
public class BragController {
	
	static final Logger logger = LoggerFactory.getLogger(BragController.class);
	
	@Autowired
	private BragService bragService;
	
	@GetMapping("/write")
	public String insertBrag() {
		return "brag/BragWrite";
	}
	
	@PostMapping("/write")
	public String saveBrag(Brag brag, String cid) {
		System.out.println(cid);
		String id = "test";
		bragService.saveBrag(brag, cid, id);
		logger.info("입력성공");
		return "redirect:/brag/write";
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
	
	@RequestMapping
	public void savePrefer(Integer bid) {
		String id = "test";
		bragService.savePrefer(bid, id);
		
	}
	 
	
}
