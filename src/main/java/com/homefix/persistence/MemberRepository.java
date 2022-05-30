package com.homefix.persistence;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{
	//이메일 중복체크
	public List<Member> findByEmail(String email);
	//아이디 중복체크
	public long countById(String id);
	//닉네임 중복체크
	public List<Member> findByNickname(String nickname);
	//회원가입
	public List<Member> findAll();
	
	//로그인
	public List<Member> findByIdAndPassword(String id, String password);
	
	//임시비밀번호 발급-------------------------	
	public Member findMemberById(String email);
	
	
	
	
//	//임시비밀번호 발급-------------------------	
//	public Optional<Member> findById(String email);
//	
//	public Member findMemberByEmail(String email);

}
