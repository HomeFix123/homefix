package com.homefix.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.homefix.domain.Member;
import com.homefix.persistence.MemberRepository;

@Service
public class MemberUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
		//MemberRepository로 회원 정보를 조회하여
		//UserDetails 타입의 객체로 리턴한다.
		//SecurityUser 객체는 userdetails.User을상속했고 User은 UserDetails를 구현한 클래스로 loadUserByUsername()메소드의 리턴타입으로 사용가능
		Optional<Member> optional = memberRepo.findById(id);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(id + "사용자 없음");
		}else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
	}
	
}
