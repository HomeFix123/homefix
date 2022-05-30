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

	//임시비밀번호 발급용
//	public String sendForgotPassword(String email);
	
	//임시비밀번호발급용 test
	public boolean userEmailCheck(String email, String name);

	public void updatePassword(Member mem, String id, String password);

	//로그인
	public String login(Member mem);
}
