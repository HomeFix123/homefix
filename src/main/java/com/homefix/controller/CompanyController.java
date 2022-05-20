package com.homefix.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.service.CompanyService;

@Controller
public class CompanyController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	CompanyService companyService;

	@RequestMapping("/{step}.do")
	public String viewPage(@PathVariable String step) {
		return step;
	}

	// 사업자 아이디 중복 조회

	@GetMapping("/idCheck")
	@ResponseBody
	public String idCheck(String id) {
		return companyService.idCheck(id);
	}

	// 사업자 이메일 중복 조회

	@GetMapping("/emailCheck")
	@ResponseBody
	public String emailCheck(String email) {
		return companyService.emailCheck(email);
	}

	// 사업자번호 중복 조회
	@GetMapping("/companyNumberCheck")
	@ResponseBody
	public String companyNumberCheck(String num) {
		return companyService.companyNumberCheck(num);
	}

	// 로그인하기

	@GetMapping("/regist")

	public String loginCheck(Company com, HttpSession session, Model model) {
		model.addAttribute("message", companyService.login(com))
		
		
		if("Y" == companyService.login(com) ) {
			System.out.println("*******로그인 성공********");
			session.setAttribute("logemail",com.getId());
			session.setAttribute("admin", com.get);
			session.setMaxInactiveInterval(60*60*24);
		}
		return "profile";
	}

	/*
	 * // 메인에서 사업자 로그인 페이지로 이동
	 * 
	 * @GetMapping("companyLogin.do") public String login() { return "sign-in"; }
	 * 
	 * // 사업자 로그인 성공후 페이지 이동->정은 물어보기
	 * 
	 * @GetMapping("loginSucess.do") public String loginMove() { return ""; }
	 * 
	 * // 사업자 로그아웃
	 * 
	 * @GetMapping public String logout(HttpServletRequest request) { HttpSession
	 * session = request.getSession();
	 * System.out.println(session.getAttribute("logemail") + "님 로그아웃");
	 * session.invalidate(); return ""; }
	 * 
	 * // 사업자 회원 탈퇴
	 * 
	 * @DeleteMapping public String companyDelete(String id) {
	 * companyService.companyDelete(id); return ""; }
	 * 
	 * // 사업자 정보수정
	 * 
	 * @PutMapping public String companyUpdate(Company com) {
	 * companyService.companyUpdate(com); return ""; }
	 */
	// 사업자 회원가입

	@PostMapping("/signUpB")
	public String companyInsert(Company com) {
		com.setEnabled(true);
		companyService.companyInsert(com);
		return "index";
	}

}
