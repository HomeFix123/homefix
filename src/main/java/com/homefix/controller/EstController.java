package com.homefix.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Chatting;
import com.homefix.domain.Contract;
import com.homefix.domain.Estimation;
import com.homefix.service.EstService;

/**
 * @author 영은
 *
 */
@Controller
@RequestMapping("/estimation")
public class EstController {
	
	static final Logger logger = LoggerFactory.getLogger(EstController.class);
	
	@Autowired
	private EstService estService;
	
	//특정 mapping 없을 경우 url과 동일한 파일 연결
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
	}
	
	//고객이 직접 회사 골라서 신청한 회사별 견정신청서 리스트 불러오기
	@GetMapping("/Chosen")
	public String queryAnno(Model m,Integer page,HttpSession session) {
		String cid = (String)session.getAttribute("userId");
		if(page==null) page=1;
		m.addAttribute("Lists",estService.getCEsts(cid , page));
		m.addAttribute("cntCEst",estService.countCEst(cid));
		return "estimation/Chosen";
	}
	
	//고객이 직접 회사 고른거 리스트 상세보기
	@GetMapping("/ChosenDetail")
	public String estDetail(Integer eid,Model m,HttpSession session) {
		System.out.println("넘어온 아이디는"+eid);
		String cid = (String)session.getAttribute("userId");
		m.addAttribute("Detail",estService.getEstDetail(eid,cid));
		return "estimation/ChosenDetail";
	}
	
	//고객 본인이 보낸 견적리스트
	@GetMapping("/MyEstimate")
	public String mEstimation(Model m,Integer page,HttpSession session) {
		String id = (String)session.getAttribute("memberId");
		logger.info("넘어온 아이디는"+id);
		if(page==null) page=1;
		System.out.println(estService.countMEList(id));
		m.addAttribute("Lists",estService.getMEstimation(id,page));
		m.addAttribute("cntMELst",estService.countMEList(id));
		return "estimation/MyEstimate";
	}
	
	//업체의 현재 진행중인 견적 리스트 
	@GetMapping("/Progress")
	public String getCIngList(HttpSession session,Model m, String sit, Integer page) {
		String cid = (String)session.getAttribute("userId");
		System.out.println("출력" + cid);
		if(cid == null) {
			return "redirect:/index";
		}
		if(page == null) {
			page=1;
		}
		
		
		Page<Contract> result = estService.getCIngList(cid, sit, page); 
		m.addAttribute("Lists", result.getContent());
		m.addAttribute("pageCnt", result.getTotalPages());
		return "estimation/Progress";
	}
	
	//내(고객) 견적 리스트 상세보기
	@GetMapping("/MyDetail")
	public String getMEDetail(Integer id, Model m) {
		System.out.println("integer 잘 넘어왔니 " + id);
		m.addAttribute("Detail",estService.getMEDetail(id));
		m.addAttribute("CLists",estService.getMEDetailC(id));
		return "estimation/MyDetail";
	}
	
	@GetMapping("/estimationtest")
	public void estimationtest() {}
	
	@RequestMapping("/estimationDetail")
	@ResponseBody
	public Estimation estimationDetail(Integer id) {
		return estService.getMEDetail(id);
	}
	
	//시공완료 버튼 선택시 db 시공완료로 변경
	@RequestMapping("/complete")
	@ResponseBody
	public void complete(Integer id) {
		System.out.println(id);
		estService.saveIng(id);
	}
	
	//업체에게 온 견적 상세보기에서 확정하기 클릭 시  esti_request테이블에 값 저장
	@GetMapping("/confirmation")
	@ResponseBody
	public void saveEstiReq(Integer eid,String cid) {
		System.out.println("넘어온 eid 값은"+eid);
		System.out.println("넘어온 cid 값은"+cid);
		estService.saveEstReq(eid, cid);
	}
	
	//업체에게 온 견적 상세보기에서 채팅하기 클릭 시 chat테이블에 값 저장
	@GetMapping("/saveChatRoom")
	@ResponseBody
	public Chatting saveChatRoom(String id,String cid,String nickname) {
		System.out.println("넘어온 id 값은 "+id);
		System.out.println("넘어온 cid 값은 "+cid);
		System.out.println("넘어온 nickname 값은 "+nickname);
		return estService.saveChatRoom(id, cid, nickname);
	}
	
	//업체에게 온 견적 상세보기에서 esti_request db 값 확인 후 버튼 변경
	@GetMapping("/getEstiReq")
	@ResponseBody
	public String getEstiReq(Integer eid, String cid, HttpSession session) {
		cid =(String) session.getAttribute("userId");

		System.out.println(eid);
		System.out.println(cid);

		return estService.getEstiReq(eid, cid);
	} 
	
	//고객 견적 상세보기에서 회사 확정하기 누르면 contract db에 저장
	@RequestMapping("/saveContract")
	@ResponseBody
	public void saveContract(Integer eid,String cid) {
		//System.out.println(eid + "와" + cid);
		estService.saveContract(eid, cid);
	}
	
	/* contract db에 견적 id가 존재하다면 버튼 변경 */
	@RequestMapping("/checkContract")
	@ResponseBody
	public Contract checkContract(Integer eid) {
		System.out.println("checkContract 실행"+eid);
		Contract contract = estService.checkContract(eid);
		return contract;
	}
	
}
