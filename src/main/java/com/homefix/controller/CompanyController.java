package com.homefix.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Company;
import com.homefix.domain.Role;
import com.homefix.mail.CompanySendEmailService;
import com.homefix.mail.MailDto;
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

	@Autowired
	CompanySendEmailService sendEmailService;

	@Autowired
	private PasswordEncoder encoder;

	// 사업자 아이디 중복 조회
	@GetMapping("/company/idCheck")
	@ResponseBody
	public String idCheck(String id) {
		return companyService.idCheck(id);
	}

	// 사업자 이메일 중복 조회
	@GetMapping("/company/emailCheck")
	@ResponseBody
	public String emailCheck(String email, String email2) {
		if (email.equals(email2)) {
			return "S";
		} else {
			return companyService.emailCheck(email);
		}
	}

	// 사업자번호 중복 조회
	@GetMapping("/company/companyNumberCheck")
	@ResponseBody
	public String companyNumberCheck(String num) {
		return companyService.companyNumberCheck(num);
	}

	// 로그인하기
	@PostMapping("/company/companyLogin")
	public String loginCheck(Company com, HttpSession session, Model model) {
		if (companyService.login(com) != null) {
			session.setAttribute("userId", com.getId());
			session.setAttribute("companyName", companyService.login(com));
			model.addAttribute("message", "Y");
			return "redirect:/index";
		} else {
			model.addAttribute("message", "N");
			return "sign/sign-in";
		}
	}

	@GetMapping("")
	public String signIn(HttpSession session) {
		if (session.getAttribute("userId") != null) {
			return "redirect:/company/profile";
		}
		return "sign/sign-in";
	}

	// 회원가입 동의 페이지 이동
	@GetMapping("/company/sign-agree-b")
	public void signAgree() {
	}

	// 사업자 회원가입 페이지 이동
	@GetMapping("/company/sign-up-b")
	public void signUpBusniss() {
	}

	// 사업자 정보수정
	@PutMapping("/company/companyUpdate")
	public String companyUpdate(Company com) {
		com.setPass(encoder.encode(com.getPass()));
		companyService.companyUpdate(com);
		return "redirect:/company/profile";
	}

	// 사업자 회원 탈퇴
	@DeleteMapping("/Withdrawal")
	@ResponseBody
	public String companyDelete(String pass, HttpSession session) {
		Company com = new Company();
		com.setPass(pass);
		com.setId((String) session.getAttribute("userId"));
		String passCheck = companyService.companyDelete(com);
		if (passCheck == "Y") {
			session.invalidate();
			return passCheck;
		} else {
			return passCheck;
		}

	}

	// 사업자 로그아웃
	@GetMapping("/logOut")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index";
	}

	// 사업자 회원가입
	@PostMapping("/company/signUpB")
	public String companyInsert(Company com) {
		com.setEnabled(true);
		com.setPass(encoder.encode(com.getPass()));
		com.setRole(Role.ROLE_COMPANY);
		String num = com.getNum();
		com.setNum(num.replaceAll("-", ""));
		companyService.companyInsert(com);
		return "redirect:/index";
	}

	// 권한 없는 사용자 에러페이지
	@GetMapping("/error")
	public String accessDeniedPage() {
		return "/sign/accessDenied";
	}

	// 아이디 비밀번호 찾기 페이지로 이동
	@GetMapping("/companyfindIdPw")
	public void findAccount() {
	}

	// 비밀번호 임시 발급
	// Email과 name의 일치여부를 check하는 컨트롤러
	@GetMapping("/checkFindPw")

	public @ResponseBody Map<String, Boolean> pw_find(String email, String id) {
		Map<String, Boolean> json = new HashMap<>();
		System.out.println("제이슨 :: " + json);
		boolean pwFindCheck = companyService.companyEmailCheck(email, id);
		System.out.println("이메일 :: " + email);
		System.out.println("아이디 :: " + id);
		json.put("check", pwFindCheck);
		return json;
	}

	// 등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@PostMapping("/checkSendEmail")
	public @ResponseBody void sendEmail(String email, String id) {
		MailDto dto = sendEmailService.createMailAndChangePassword(email, id);
		sendEmailService.mailSend(dto);
	}

	// 아이디찾기
	@GetMapping("/checkFindId")
	@ResponseBody
	public String findCompanyId(String ceo, String tel) {
		return companyService.companyNameTelCheck(ceo, tel).getId();

	}
}
