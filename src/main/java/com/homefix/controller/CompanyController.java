package com.homefix.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.service.CompanyService;

/**
 * @author 이은혜
 *
 */


@Controller
@RequestMapping("/sign")
public class CompanyController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	CompanyService companyService;

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
	@GetMapping("/companyLogin")
	public String loginCheck(Company com, HttpSession session, Model model) {
		if(companyService.login(com)!=null) {
			System.out.println("*******로그인 성공********");
			session.setAttribute("userId",com.getId()); 
			session.setAttribute("companyName",companyService.login(com) );
			model.addAttribute("message", "Y");
			return "redirect:/company/companyprofile"; 
		}else {
			model.addAttribute("message", "N");
			System.out.println("*******로그인 실패*********");
			return "/sign/sign-in";
		}
	}

	
	


	// 회원가입 동의 페이지 이동
	@GetMapping("sign-agree-b")
	public void signAgree() {

	}

	// 사업자 회원가입 페이지 이동
	@GetMapping("sign-up-b")
	public void signUpBusniss() {

	}

	/*
	 * // 사업자 로그인 성공후 페이지 이동->정은 물어보기
	 * @GetMapping("loginSucess.do") public String loginMove() { return ""; }
	 * 
	 * 
	 * 
	 * // 사업자 로그아웃
	 * @GetMapping public String logout(HttpServletRequest request) { HttpSession
	 * session = request.getSession();
	 * System.out.println(session.getAttribute("logemail") + "님 로그아웃");
	 * session.invalidate(); return ""; }
	 * 
	 * // 사업자 회원 탈퇴
	 * @DeleteMapping public String companyDelete(String id) {
	 * companyService.companyDelete(id); return ""; }
	 * 
	 * // 사업자 정보수정
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
	
	/* 비밀번호 찾기 */
	/*
	 * @RequestMapping(value = "findIdPw", method = RequestMethod.GET) public void
	 * findIdPwGET() throws Exception{ }
	 * 
	 * @RequestMapping(value = "findIdPw", method = RequestMethod.POST) public void
	 * findIdPwPOST(Company com, HttpServletResponse response) throws Exception{
	 * companyService.findIdPwPOST(response, com); }
	 */

}
