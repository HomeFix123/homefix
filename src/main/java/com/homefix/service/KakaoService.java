package com.homefix.service;

import java.util.HashMap;



public interface KakaoService {
	
	String getKakaoAccessToken(String code);

	HashMap<String, Object> getUserInfo(String access_Token);
	
}
