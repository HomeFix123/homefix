package com.homefix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.homefix.domain.Estimation;
import com.homefix.service.EstService2;

@Controller
@RequestMapping("/estimation") // 뷰폴더 이름이 아니라 요청하는 url 주소
public class EstController2 {
	
	static final Logger logger = LoggerFactory.getLogger(EstController2.class);
	
	@Autowired
	private EstService2 estService2;
	
//	@GetMapping("/{step}")
//	public void viewPage(@PathVariable String step) {
//		//return "/estimation2/" + step;
//	}
	
	/* 견적상담 신청 페이지 */
	@GetMapping("/write") //RESTful 방식 : /estimation/write
	public String insertEst(HttpSession session) {
		if ( session.getAttribute("memberId")== null) {
			return "redirect:/sign";
		} 
		//업체 상세페이지에서 업체 아이디 넘어가는 것 확인 완료//System.out.println(id);
		return "estimation2/estimation"; // RESTful은 리턴값을 반드시 써주어야 함(리턴은 뷰페이지 경로 적기)
	}
		
	@PostMapping("/write")
	public String saveEst(Estimation est, HttpSession session,String cid) {
		String id = (String) session.getAttribute("memberId"); // 나중에는 세션에서 ID 값을 가져옴, 현재 테스트로 ID 직접 넣어줌
		System.out.println(cid);
		if(cid != null) {
			estService2.saveEst(est, id, cid);
			return "redirect:write";
		}
		estService2.saveEst(est, id);
		logger.info("입력성공");
		return "redirect:write"; //redirect는 요청 url 주소를 써줌
	}
	
	/* Q&A 페이지 */
	@GetMapping("/qna")
	public String qna() {
		return "estimation2/qna";
	}
	
	/* 전체견적 목록 페이지 */
	@GetMapping("/total")
	public String getEstList(Model m, Integer page,HttpSession session) {
		if(page == null) page = 1;
		Estimation est = new Estimation(); 
		/*List<Estimation> list = estService2.getEstList(est, page);*/ 
		String cid = (String)session.getAttribute("userId");
		m.addAttribute("estList", estService2.getEstList(est,cid, page)); 
		m.addAttribute("cntEst", estService2.countEstList());
		return "estimation2/est-total-list";
	}
	
	/* 견적희망서(상세) 페이지 */
	@GetMapping("/details")
	public String estDetails(Integer id, Model m,HttpSession session) {
		m.addAttribute("Details", estService2.getEstDetails(id));
		return "estimation2/estimation-details";
	}
		
}
