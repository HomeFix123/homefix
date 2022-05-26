package com.homefix.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Member;
import com.homefix.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepo;
	
	//이메일 유효성 체크
	@Override
	public String memEmail(String email) {
		String message = "Y";
		if (memberRepo.findByEmail(email).size() > 0) {
			message= "N";
		}
		System.out.println(memberRepo.findByEmail(email));
		System.out.println(email+"여기까지 와라");
		return message;
	}
	
	//아이디 중복 체크
	@Override
	public String memIdChack(String id) {
		System.out.println(memberRepo.countById(id));
		String message = "Y";
		if (memberRepo.countById(id) > 0) {
			message= "N";
		}
		System.out.println(id+"여기까지 와라");
		System.out.println(memberRepo.countById(id));
		return message;
	}

	//닉네임 중복 체크
	@Override
	public String memNickChack(String nickname) {
		String message = "Y";
		if (memberRepo.findByNickname(nickname).size() > 0) {
			message= "N";
		}
		System.out.println(memberRepo.findByEmail(nickname));
		System.out.println(nickname+"닉네임 중복체크 여기까지옴");
		return message;
	}

	//회원가입
	@Override
	public void memberInsert(Member mem) {
		memberRepo.save(mem);
		
	}
	

}
