package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;
import com.homefix.service.CompanyService;
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

	// 사업체 마이페이지-정보수정페이지 정보 불러오기
	@GetMapping("/profile")
	public String getCompanyMypage(HttpSession session, Model model) {

		////////시공 중인 글목록보기
		
		
		
		////////견적 신청 중인 글목록보기
		
		
		
		
		/////////구독관리(결제정보)
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));
		List<Payment> list = paymentService.RemainingDate(com);
		for (Payment pay : list) {
			System.out.println("@@@@@@@@@@@@@@@@@@" + pay.toString());
		}
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
	@GetMapping("")
	public String companyDetailPage() {

		return "company/companydetails";
	}
	
	
	

}
