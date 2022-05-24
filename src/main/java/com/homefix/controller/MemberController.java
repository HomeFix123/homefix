package com.homefix.controller;

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
import com.homefix.service.MemberService;

@Controller
@RequestMapping("/sign")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	MemberService memberService;

	// 사업자 아이디 중복 조회
	@GetMapping("/idCheck")
	@ResponseBody
	public String idCheck(String id) {
		return memberService.idCheck(id);
	}

	// 사업자 이메일 중복 조회
	@GetMapping("/emailCheck")
	@ResponseBody
	public String emailCheck(String email) {
		return memberService.emailCheck(email);
	}

	// 로그인하기
	@GetMapping("/companyLogin")
	public String loginCheck(Company com, HttpSession session, Model model) {
		if(memberService.login(com)!=null) {
			System.out.println("*******로그인 성공********");
			session.setAttribute("userId",com.getId()); 
			session.setAttribute("companyName",memberService.login(com).get(0).getName() );
			//session.setMaxInactiveInterval(60*60*24); //세션유지시간:초*분*시간
			return "redirect:/company/companyprofile"; 
		}else {
			System.out.println("*******로그인 실패*********");
			return "redirect:/sign/sign-in";
		}
	}

	
	
	// 메인에서 사업자 로그인 페이지로 이동
	@GetMapping("sign-in")
	public void login() {

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
		memberService.companyInsert(com);
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
