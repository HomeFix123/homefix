package com.homefix.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.homefix.domain.Company;
import com.homefix.domain.Member;

public class SecurityUser extends User {
	private static final long serialVersionUID = 1L;
	
	public SecurityUser(Company company) {
		super(company.getId(),"{noop}"+company.getPass(),  //스프링 부트 버전 업으로 {noop}을 붙여 비밀번호 인코딩하기
				AuthorityUtils.createAuthorityList(company.getRole().toString()) );
	}
	
	
	  public SecurityUser(Member member) {
	      super(member.getId(),"{noop}"+member.getPassword(), 
	            AuthorityUtils.createAuthorityList(member.getRole().toString()) );
	   }
	
}
