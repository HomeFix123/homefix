package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.homefix.service.KakaoServiceImpl;

import lombok.AllArgsConstructor;

//@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {
	
	private KakaoServiceImpl kakao;

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
    public String login(@RequestParam String code) {
        String access_Token = kakao.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        System.out.println("hhh");
        
        return "/kakao/loginkakao";
    }
}
