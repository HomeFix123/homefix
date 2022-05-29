package com.homefix.controller;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Member;
import com.homefix.service.MemberService;


@Controller
@RequestMapping("/sign")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	//로그인 페이지로 이동
	@GetMapping(path ="/sign-in")
	public void login() {
		logger.info("로그인");	
	}
	
	//회원가입 페이지로 이동
	@GetMapping(path ="member/sign_member")
	public String signUpMember() {
		logger.info("개인 회원가입");
		return "/sign/member/sign_member";
	}
	
	// 아이디 중복 조회
	@GetMapping("/member/memIdChack")
	@ResponseBody
	public String memIdChack(String id) {
		return memberService.memIdChack(id);
	}
	
	// 아이디 중복 조회
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

}
