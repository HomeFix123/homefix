package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;
import com.homefix.domain.Review;
import com.homefix.service.CompanyService;
import com.homefix.service.EstService;
import com.homefix.service.PaymentService;

/**
 * @author 이은혜
 *
 */

@Controller
@RequestMapping("/company")
public class CompanyPageController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	CompanyService companyService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	EstService estService;

	// 사업체 마이페이지
	@GetMapping("/profile")
	public String getCompanyMypage(HttpSession session, Model model, Integer page) {

		//////// 시공 중인 글목록보기- vo Contract /progress
		// model.addAttribute("IngList",estService.getCIngList((String)
		//////// session.getAttribute("userId")));

		//////// 견적 신청 중인 글목록보기- vo Estimation / chosen
		model.addAttribute("RegistList", estService.getCEst((String) session.getAttribute("userId")));

		//Integer page = 1;
		//사업체가 직접 쓴 후기 보기
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));
		if(page == null) {page=1;}
		model.addAttribute("Review", companyService.getCompanyReview(com, page));
		
		 
		///////// 구독관리(결제정보)
		//Company com = new Company(); 
		///com.setId((String) session.getAttribute("userId"));
		List<Payment> list = paymentService.RemainDate(com,page);

		if (list.size() > 0) {
			model.addAttribute("PaymentInfo", list);
		}

		/////// 마이정보수정
		if (session.getAttribute("userId") != null) {
			String id = session.getAttribute("userId").toString();
			model.addAttribute("company", companyService.getCompanyMyInfo(id));
			return "/company/companyprofile";
		} else {
			return "redirect:/sign";
		}
	}

	// 사업체 상세페이지 로딩
	@GetMapping("/{id}")
	public String companyDetailPage(@PathVariable String id, Model model, Integer page ) {
		if(page == null) {page=1;}
		// @@테스트 임시 셋팅@@
		Company com = new Company();
		com.setId(id);
		//com.setId("905");

		// 업체명 및 로고 기타//전문분야:companyspecial/시공횟수:company
		// 1.업체정보//vo:Company
		//model.addAttribute("Spcial", companyService.getCompanySpecial(com));
		
		Company coms = companyService.getCompanyMyInfo(com.getId());
		if(coms.getCnt()==null) {
			coms.setCnt(0); //시공횟수가 null값이면 0으로 지정.
		}
		model.addAttribute("CompanyBasic", coms);

		// 2.업체소개//vo:CompanyInfo
		model.addAttribute("CompanyInfo", companyService.getCompanyIntroduction(com));

		// 3.시공사례(인테리어 자랑)//vo:brag
		model.addAttribute("Brag", companyService.getInteriorBrag(com));

		// 4.업체후기(시공후기)//vo:Review
		if(page==null)page=1;
		model.addAttribute("Review", companyService.getCompanyReview(com, page));

		return "company/companydetails";
	}

	
	@GetMapping("/myRiew")
	@ResponseBody
	public List<Review> getMyRiew(HttpSession session,   Integer page) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+session.getAttribute("userId"));
		
		Company com = new Company();
		com.setId((String)session.getAttribute("userId"));
		
		List<Review> reviewList= companyService.getCompanyReview(com, page);
		return reviewList;
	}
	
	
}
