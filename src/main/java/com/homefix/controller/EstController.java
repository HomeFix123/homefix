package com.homefix.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Estimation;
import com.homefix.persistence.EstRepository;
import com.homefix.service.EstService;

@Controller
@RequestMapping("/estimation")
public class EstController {
	@Autowired
	private EstService estService;
	@Autowired
	private EstRepository estRepo;
	
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
	
	@GetMapping("/MPickC")
	public String queryAnno(Model m,String cid) {
		m.addAttribute("Lists",estService.getCEst(cid));
		return "/estimation/MPickC";
	}
	
	@GetMapping("/MPickCDetail")
	public String estDetail(String id,Model m) {
		System.out.println("넘어온 아이디는"+id);
		m.addAttribute("Detail",estService.getEstDetail(id));
		return "/estimation/MPickCDetail";
	}
	
	//ajax로 session에 값 저장하고 출력하는거 테스트 후에 지우기!!!!!!!!!!!!!!!!!
	@RequestMapping("/sessiontest")
	@ResponseBody
	public String sessioncheck(String id , HttpSession session) {
		session.setAttribute("id", id);
		String sessionid = (String)session.getAttribute("id");
		//System.out.println(sessionid);
		return sessionid;
	}
}
