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
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.Payment;
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
	public String getCompanyMypage(HttpSession session, Model model) {

		//////// 시공 중인 글목록보기- vo Contract /progress
		// model.addAttribute("IngList",estService.getCIngList((String)
		//////// session.getAttribute("userId")));

		//////// 견적 신청 중인 글목록보기- vo Estimation / chosen
		model.addAttribute("RegistList", estService.getCEst((String) session.getAttribute("userId"),1));

		///////// 구독관리(결제정보)
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));
		List<Payment> list = paymentService.RemainingDate(com);

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
	public String companyDetailPage(Company com, Model model) {
		// @@테스트 임시 셋팅@@
		com.setId("905");

		// 업체명 및 로고 기타//전문분야:companyspecial/시공횟수:company
		// 1.업체정보//vo:Company
		//model.addAttribute("Spcial", companyService.getCompanySpecial(com));
		model.addAttribute("CompanyBasic", companyService.getCompanyMyInfo(com.getId()));

		// 2.업체소개//vo:CompanyInfo
		model.addAttribute("CompanyInfo", companyService.getCompanyIntroduction(com));

		// 3.시공사례(인테리어 자랑)//vo:brag
		model.addAttribute("Brag", companyService.getInteriorBrag(com));

		// 4.업체후기(시공후기)//vo:Review
		model.addAttribute("Review", companyService.getCompanyReview(com));

		return "company/companydetails";
	}

}
