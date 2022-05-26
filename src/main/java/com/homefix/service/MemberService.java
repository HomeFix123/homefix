package com.homefix.service;

import com.homefix.domain.Member;

public interface MemberService {

	//이메일 중복조회
	public String memEmail(String email);

	//아이디 중복조회
	public String memIdChack(String id);

	//닉네임 중복조회
	public String memNickChack(String nickname);

	//회원가입
	public void memberInsert(Member mem);
	
	
	
}
