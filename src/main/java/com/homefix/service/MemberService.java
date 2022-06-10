package com.homefix.service;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.homefix.domain.Brag;
import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.Member;
import com.homefix.domain.Prefer;
import com.homefix.domain.Tip;

public interface MemberService {

	//이메일 중복조회
	public String memEmail(String email);

	//아이디 중복조회
	public String memIdChack(String id);

	//닉네임 중복조회
	public String memNickChack(String nickname);

	//회원가입
	public void memberInsert(Member mem);

	//로그인
	public Member login(Member mem);

	//회원정보 수정
	public void updateMember(Member mem);

	
	//임시 비밀번호 발급용
	public boolean userEmailCheck(String email, String id);

	//마이페이지
	public List<Member> myPageList(Member mem);

	//멤버 탈퇴
	public String memberDelete(Member mem);
	
	//고객 본인이 쓴 글 불러오기 , int page
	public List<Brag> getMyReviewList(String id);
	
	//고객이 쓴 팁 글 불러오기
	public List<Tip> getMyTip(String id);
	
	//고객이 좋아요 찍은 글 불러오기
	public List<Prefer> getMyLove(String id);
	
	//고객이 좋아요 찍은 업체 목록 불러오기
	public List<CompanyPrefer> getMyLoveCompany(String id);


}
