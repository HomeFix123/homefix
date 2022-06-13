package com.homefix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 시큐리티 설정파일을 의미하는 어노테이션(시큐리티 사용에 필요한 객체 생성)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CompanyUserDetailsService companyUserDetailsService;

	@Override // ***오타나면 페이지 안뜨므로 조심!
	protected void configure(HttpSecurity security) throws Exception {

		security.authorizeRequests().antMatchers("/index" ,
												 "/sign/**",
												 "/company/{id}",
												 "/sign/member/sign_kakao",
												 "/sign/member/sign_member"
												 ,"/brag/**","/review/**","/expert","/estimation/**"
												 ).permitAll(); // index화면에 모두 접근가능.
		
		security.authorizeRequests().antMatchers("-").authenticated(); // 인증된 사용자만
		security.authorizeRequests().antMatchers("/member/profile").hasRole("USER");
		//security.authorizeRequests().antMatchers("/member/**").authenticated(); // 인증된 사용자만
		security.authorizeRequests().antMatchers("/company/profile").hasRole("COMPANY");// manager만
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");// admin만
		security.formLogin().loginPage("/sign"); // 사용자 로그인 화면
		//security.formLogin().loginPage("/sign").defaultSuccessUrl("/sign/company/companyLogin", true); // 사용자 로그인 화면
		security.exceptionHandling().accessDeniedPage("/sign/error");// 권한 없을 떄 보여주는 오류 페이지 지정하기
		security.logout().invalidateHttpSession(true).logoutSuccessUrl("/sign");// 로그아웃->연관된 세션 강제종료->로그아웃후 리다이렉트
		security.userDetailsService(companyUserDetailsService);
		security.csrf().disable();// Restfull 사용자는 csrf는 disable로 설정한다.
	}
	
	// 비밀번호 인코더
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
