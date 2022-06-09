package com.homefix.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.persistence.CompanyRepository;

@Service
public class CompanyUserDetailsService implements UserDetailsService {

	@Autowired
	private CompanyRepository companyRepo;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		// CompanyRepository로 회원 정보를 조회하여
		// UserDetails 타입의 객체로 리턴한다.
		// SecurityUser 객체는 userdetails.User을상속했고 User은 UserDetails를 구현한 클래스로
		// loadUserByUsername()메소드의 리턴타입으로 사용가능
		Optional<Company> optional = companyRepo.findById(id);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(id + "사용자 없음");
		} else {
			Company company = optional.get();
			return new SecurityUser(company);
		}
		
		
		
		
	}

}
