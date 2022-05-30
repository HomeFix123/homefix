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
import com.homefix.persistence.Esti_requestRepository;
import com.homefix.service.EstService;

@Controller
@RequestMapping("/estimation")
public class EstController {
	@Autowired
	private EstService estService;
	
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
	
	//고객이 직접 회사 골라서 신청한 회사별 견정신청서 리스트 불러오기
	@GetMapping("/Chosen")
	public String queryAnno(Model m,String cid) {
		m.addAttribute("Lists",estService.getCEst(cid));
		return "estimation/MPickC";
	}
	
	//고객이 직접 회사 고른거 리스트 상세보기
	@GetMapping("/ChosenDetail")
	public String estDetail(String id,Model m) {
		System.out.println("넘어온 아이디는"+id);
		m.addAttribute("Detail",estService.getEstDetail(id));
		return "estimation/MPickCDetail";
	}
	
	//고객 본인이 보낸 견적리스트
	@GetMapping("/MyEstimate")
	public String mEstimation(String id,Model m) {
		System.out.println("넘어온 아이디는"+id);
		m.addAttribute("Lists",estService.getMEstimation(id));
		return "estimation/MEstimation";
	}
	
	//업체의 현재 진행중인 견적 리스트 
	@GetMapping("/Progress")
	public String getCIngList(String cid,Model m) {
		System.out.println("넘어온 cid값은 "+cid);
		m.addAttribute("Lists",estService.getCIngList(cid));
		return "estimation/CIng";
	}
	
	//내(고객) 견적 리스트 상세보기
	@GetMapping("/MyDetail")
	public String getMEDetail(Integer id, Model m) {
		System.out.println("integer 잘 넘어왔니 " + id);
		m.addAttribute("Detail",estService.getMEDetail(id));
		m.addAttribute("Company",estService.getMEDetailC(id));
		return "estimation/MEDetail";
	}
	
	@GetMapping("/estimationtest")
	public void estimationtest() {}
	
	//ajax로 session에 값 저장하고 출력하는거 테스트 후에 지우기!!!!!!!!!!!!!!!!!
	@RequestMapping("/sessiontest")
	@ResponseBody
	public String sessioncheck(String id , HttpSession session) {
		session.setAttribute("id", id);
		String sessionid = (String)session.getAttribute("id");
		//System.out.println(sessionid);
		return sessionid;
	}
	
	@RequestMapping("/estimationDetail")
	@ResponseBody
	public Estimation estimationDetail(Integer id) {
		
		return estService.getMEDetail(id);
	}
}
