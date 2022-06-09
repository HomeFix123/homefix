package com.homefix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homefix.domain.Member;
import com.homefix.service.KakaoService;

import lombok.AllArgsConstructor;

//@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {
	
	@Autowired
	private KakaoService kakao;

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
//    @ResponseBody
//    @GetMapping("/kakao")
//    public void kakaoCallback(@RequestParam String code) {
//        System.out.println("!!!!!!!!!!!!!!!코드확인!!!!!!!!!!!!!!!!!!!::: "+code);
//    }
    
    
    
    @RequestMapping(value="/kakao")
    public String login(@RequestParam String code, RedirectAttributes redirectAttr, HttpSession session) {
        String access_Token = kakao.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        System.out.println("로그인 성공");
        
        String kakaoId = kakao.getUserKakaoId(access_Token);
        
        Member member = kakao.loginKakao(kakaoId);
        
        if(member == null) {
        	System.out.println("카카오:"+kakaoId);
        	redirectAttr.addFlashAttribute("kakao", kakaoId);
        	return "redirect:/kakao/sign";
        }
        
        session.setAttribute("memberId", member.getId());
        
        
        return "redirect:/index";
    }
    
    @GetMapping("/sign")
    public String signKakao() {
    	
    	
    	return "/sign/member/sign_kakao";
    }
}
