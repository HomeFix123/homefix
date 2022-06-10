package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;
import com.homefix.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	/**
	 * @author 이은혜
	 *
	 */

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	PaymentService paymentService;

	// 결제 페이지에서 마지막 구독일 조회
	@GetMapping("")
	public String paymentPage(HttpSession session, Model model) {
		Company company = new Company();
		if (session.getAttribute("userId") != null) {
			company.setId((String) session.getAttribute("userId"));
			List<Payment> list = paymentService.RemainingDate(company);

			if (list.size() > 0) {
				for (Payment pay : list) {
					model.addAttribute("EndDay", pay.getPlast());
				}
			}
		}
		return "payment/payment";
	}

	
	
	// 결제정보 DB저장
	@PostMapping("/paymentInfoInsert")
	@ResponseBody
	public void paymentInfoInsert(Payment vo) {
		paymentService.paymentInfoInsert(vo);
	}

	
	
	// 결제완료 페이지
	@GetMapping("/congrats")
	public String paymentCongrats() {
		return "payment/congrats";
	}

	
	

}
