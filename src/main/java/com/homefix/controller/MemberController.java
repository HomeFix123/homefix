package com.homefix.controller;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Member;
import com.homefix.mail.MailDto;
import com.homefix.mail.SendEmailService;
import com.homefix.service.MemberService;


@Controller
@RequestMapping("/sign")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	
	
	//로그인 페이지로 이동
	@GetMapping(path ="/sign-in")
	public void login() {
		logger.info("로그인");	
	}
	
	//아이디 비밀번호 찾기 페이지로 이동
	@GetMapping(path ="/findIdPw")
	public void findAccount() {
		logger.info("아이디 비밀번호 찾기");
	}
	
	//회원가입 페이지로 이동
	@GetMapping(path ="/member/sign_member")
	public String signUpMember() {
		logger.info("개인 회원가입");
		return "sign/member/sign_member";
	}
	
	// 아이디 중복 조회
	@GetMapping("/member/memIdChack")
	@ResponseBody
	public String memIdChack(String id) {
		return memberService.memIdChack(id);
	}
	
	// 닉네임 중복 조회
	@GetMapping("/member/memNickChack")
	@ResponseBody
	public String memNickChack(String nickname) {
		return memberService.memNickChack(nickname);
	}
	
	// 이메일 중복 조회
	@GetMapping("/member/memEmail")
	@ResponseBody
	public String memEmail(String email) {
		return memberService.memEmail(email);
	}
	
	// 회원 등록
	@PostMapping(value = "/member/memSave")
	public String memberInsert(Member mem) throws IOException {
		memberService.memberInsert(mem);
		return "redirect:/sign/sign-in";
					
	}
	
	// 로그인하기
	@PostMapping("/member/loginMem")
	public String LoginMember(Member mem, HttpSession session, Model model) {
		

		System.out.println("로그인성공했나?" + memberService.login(mem));
		if (memberService.login(mem) != null) {
			System.out.println("로그인 성공함");
			session.setAttribute("memberId", mem.getId());
			session.setAttribute("memberName", memberService.login(mem));
			model.addAttribute("message", "Y");
			return "redirect:/index";
		} else {
			model.addAttribute("message", "N");
			System.out.println("로그인 실패했음");
			System.out.println(mem.getPassword());
			System.out.println(mem.getId());
			return "sign/sign-in";
		}
	}
	
	
	@GetMapping("/member/sign-in")
	public String memLogin(HttpSession session) {
	
		if (session.getAttribute("memberId") != null) {
	
			return "/member/profile";
	
		} else {
			return "sign/sign-in";
	
		}
	
	}
	
	//개인 마이페이지
	@GetMapping(path ="/member/profile")
	public void myPage(Model m) {
		logger.info("개인 마이페이지");	
		Member mem = new Member();
		List<Member> list = memberService.myPageList(mem);
		m.addAttribute("member",list);
	}	

	public void myPage() {
		logger.info("개인 마이페이지");	

	}
	
	// 글 수정
	@PostMapping("/member/update")
	public String update(Member mem) {	
		memberService.update(mem);
		return "redirect:/member/profile";
	}

	// 임시비밀번호 발급
//	1. 임시 비밀번호를 발급받을 이메일을 입력 ( 입력한 화면 ) 확인을 누르면 이메일이 전송됨
//	2. 로그인 화면으로 이동
//	@PostMapping(value = "/member/sendEmail")
//	public String tempPasssword(Member mem) {
//		try {
//			logger.info("임시 비밀번호 발급 시작 -- ");
//			String result = memberService.sendForgotPassword(mem.getEmail());
//			if (result.equals("ok")) {
//				return "redirect:/sign/sign-in";
//			} else {
//				return "redirect:/sign/member/sign_member";
//			}
//		} catch (Exception e) {
//			System.out.println("E : " + e);
//			return "redirect:/sign/sign-in";
//		}
//	}

	
	//비밀번호 임시 발급 -------------------------------------------
	//Email과 name의 일치여부를 check하는 컨트롤러
	@GetMapping("/check/findPw")
	public @ResponseBody Map<String, Boolean> pw_find(String email, String id){
		Map<String,Boolean> json = new HashMap<>();
		System.out.println("제이슨 :: "+json);
		boolean pwFindCheck = memberService.userEmailCheck(email,id);
		System.out.println("이메일 :: "+email);	
		System.out.println("아이디 :: "+id);
		System.out.println(pwFindCheck);
		json.put("check", pwFindCheck);
		return json;
	}

	//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	@PostMapping("/check/findPw/sendEmail")
	public @ResponseBody void sendEmail(String email, String id){
	    MailDto dto = sendEmailService.createMailAndChangePassword(email, id);
	    sendEmailService.mailSend(dto);
	
	}
		
	
}
