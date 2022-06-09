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

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Estimation;
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

		if (page == null) {
			page = 1;
		}
		//////// 견적 신청 중인 글목록보기- vo Estimation / chosen
		model.addAttribute("RegistList", estService.getCEst((String) session.getAttribute("userId"), page));

		// 사업체가 직접 쓴 후기 보기
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));
		model.addAttribute("Review", companyService.getCompanyReview(com, page));

		///////// 구독관리(결제정보)
		model.addAttribute("PaymentInfo", paymentService.RemainDate(com, page));

		/////// 마이정보수정
		if (session.getAttribute("userId") != null) {
			String id = session.getAttribute("userId").toString();
			model.addAttribute("company", companyService.getCompanyMyInfo(id));
			return "company/companyprofile";
		} else {
			return "redirect:/sign";
		}
	}

	// 사업체 상세페이지 로딩
	@GetMapping("/{id}")
	public String companyDetailPage(@PathVariable String id, Model model, Integer page) {
		Company com = new Company();
		com.setId(id);

		// 업체명 및 로고 기타//전문분야:companyspecial/시공횟수:company
		// 1.업체정보//vo:Company

		Company coms = companyService.getCompanyMyInfo(com.getId());
		if (coms.getCnt() == null) {
			coms.setCnt(0); // 시공횟수가 null값이면 0으로 지정.
		}
		if (coms.getTel() == null) {
			coms.setTel(" ");
		}
		if (coms.getEmail() == null) {
			coms.setEmail(" ");
		}
		model.addAttribute("CompanyBasic", coms);

		// 2.업체소개//vo:CompanyInfo
		model.addAttribute("CompanyInfo", companyService.getCompanyIntroduction(com));

		// 3.시공사례(인테리어 자랑)//vo:brag
		if (page == null) {
			page = 1;
		}
		model.addAttribute("Brag", companyService.getInteriorBrag(com, page));

		// 4.업체후기(시공후기)//vo:Review
		model.addAttribute("Review", companyService.getCompanyReview(com, page));

		return "company/companydetails";
	}

	// 마이페이지에서 업체가 쓴 시공후기 더보기
	@GetMapping("/myRiew")
	@ResponseBody
	public List<Review> getMyRiew(HttpSession session, Integer page, String cid) {
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));

		return companyService.getCompanyReview(com, page);
	}

	// 구독관리(결제정보)더보기
	@GetMapping("/myPaymentInfo")
	@ResponseBody
	public List<Payment> getMyPaymentInfo(HttpSession session, Integer page) {
		Company com = new Company();
		com.setId((String) session.getAttribute("userId"));
		return paymentService.RemainDate(com, page);
	}

	// 신청한 견적 더보기
	@GetMapping("/registEst")
	@ResponseBody
	public List<Estimation> getRegistEstmation(HttpSession session, Integer page) {
		return estService.getCEst((String) session.getAttribute("userId"), page);
	}

	// 업체상세페이지 업체후기 더보기
	@GetMapping("/companyRiew")
	@ResponseBody
	public List<Review> getcompanyRiew(Integer page, String id) {
		Company com = new Company();
		com.setId(id);
		return companyService.getCompanyReview(com, page);
	}

	
	 //업체상세페이지 시공사례(인테리어 자랑) 더보기
	@GetMapping("/companyBrag")
	@ResponseBody
	public List<Brag> getcompanyBrag(Integer page, String id) {
		Company com = new Company();
		com.setId(id);
		return companyService.getInteriorBrag(com, page);
	}
	
}
