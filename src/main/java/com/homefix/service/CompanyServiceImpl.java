package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.Payment;
import com.homefix.persistence.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepo;

	// 사업자 아이디 중복 조회
	public String idCheck(String id) {
		String message = "Y";
		if (companyRepo.countById(id) > 0) {
			message = "N";
		}
		return message;
	}

	// 사업자 이메일 중복 조회
	public String emailCheck(String email) {
		String message = "Y";
		if (companyRepo.findByEmail(email).size() > 0) {
			message = "N";
		}
		return message;
	}

	// 사업자번호 중복 조회
	public String companyNumberCheck(String num) {
		String message = "Y";
		if (companyRepo.countByNum(num) > 0) {
			message = "N";
		}
		return message;
	}

	// 로그인 성공
	public String login(Company com) {
		List<Company> list = companyRepo.findByIdAndPass(com.getId(), com.getPass());
		String message = null;
		if (list.size() > 0) {
			message = list.get(0).getName();
		}
		return message;
	}

	// 사업자 회원 탈퇴
	public String companyDelete(Company com) {
		Company compo = companyRepo.findById(com.getId()).get();
		if (compo.getPass().equals(com.getPass()) ) {
			companyRepo.deleteById(com.getId());
			return "Y";
		}
		return "N";
	}
	
	

	// 사업자 정보 불러오기
	public Company getCompanyMyInfo(String companyId) {
		return companyRepo.findById(companyId).get();
	}

	// 사업자 정보수정
	public void companyUpdate(Company com) {
		Company company = companyRepo.findById(com.getId()).get();
		company.setAddr(com.getAddr());
		company.setAddrd(com.getAddrd());
		company.setEmail(com.getEmail());
		company.setName(com.getName());
		company.setTel(com.getTel());
		company.setPass(com.getPass());
		company.setZip(com.getZip());
		companyRepo.save(company);
	}

	// 사업자 회원가입
	public void companyInsert(Company com) {

		companyRepo.save(com);
	}

	
	
	
	

}
