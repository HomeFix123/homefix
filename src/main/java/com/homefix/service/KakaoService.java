package com.homefix.service;

import com.homefix.domain.Member;



public interface KakaoService {
	
	String getKakaoAccessToken(String code);

	String getUserKakaoId(String access_Token);
	
	public Member loginKakao(String kakaoId);
}
