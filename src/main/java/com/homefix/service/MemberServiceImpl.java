package com.homefix.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.Member;
import com.homefix.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepo;

	// 개인 아이디 중복 조회
	public String idCheck(Member mem) {
		String message= "Y";
		 if(memberRepo.countById(mem)>0) {
			 message= "N";
		 } 
		 return message;
	} 

	// 개인 이메일 중복 조회
	public String emailCheck(Member mem) {
		String message = "Y";
		if (memberRepo.findByEmail(mem.getEmail()).size() > 0) {
			message= "N";
		}
		return message;
	}

	// 개인 성공
	public List<Company> login(Member mem) {
		List<Company> message = null ;
		if ( memberRepo.findByIdAndPass(mem.getId(), mem.getPassword()).size()>0) {
			message= memberRepo.findByIdAndPass(mem.getId(), mem.getPassword());
		}
		return message;
	} 

	// 개인 회원 탈퇴
	public void companyDelete(Member mem) {
		memberRepo.deleteAllById(mem);
	}
  
	// 개인 정보수정
	public void companyUpdate(Member mem) {
		memberRepo.save(mem);
	}

	// 개인 회원가입
	public void companyInsert(Member mem) {
		memberRepo.save(mem); 
	}

	@Override
	public String idCheck(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String emailCheck(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> login(Company com) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void companyDelete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyUpdate(Company com) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyInsert(Company com) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findIdPwPOST(HttpServletResponse response, Company com) {
		// TODO Auto-generated method stub
		
	}

}
