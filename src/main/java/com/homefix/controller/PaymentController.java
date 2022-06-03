package com.homefix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/")
	public String paymentPage() {

		return "/payment/payment";
	}

	
	@PostMapping("/paymentInfoInsert")
	public String paymentInfoInsert(Payment vo) {

		paymentService.paymentInfoInsert(vo);
		
		return "/payment/congrats";
	}
}
