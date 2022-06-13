package com.homefix.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.homefix.domain.Company;
import com.homefix.domain.Member;

public class SecurityUser extends User {
	private static final long serialVersionUID = 1L;
	
	public SecurityUser(Company company) {
		super(company.getId(),company.getPass(),  
				AuthorityUtils.createAuthorityList(company.getRole().toString()) );
	}
	
	
	  public SecurityUser(Member member) {
	      super(member.getId(),member.getPassword(), 
	            AuthorityUtils.createAuthorityList(member.getRole().toString()) );
	   }
	
}
