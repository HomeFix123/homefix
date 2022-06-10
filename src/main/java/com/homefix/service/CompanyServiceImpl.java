package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.Member;
import com.homefix.domain.Review;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.CompanyInfoRepository;
import com.homefix.persistence.CompanyPreferRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.ReviewRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepo;

	@Autowired
	CompanyInfoRepository companyInfoRepo;

	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	BragRepository bragrepo;

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
	public Company login(Company com) {
		Company comm = companyRepo.findById(com.getId()).get();
		  return comm;
	}

	// 사업자 회원 탈퇴
	public String companyDelete(Company com) {
		Company compo = companyRepo.findById(com.getId()).get();
		if (compo.getPass().equals(com.getPass())) {
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
		company.setLogo(com.getLogo());
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

	// 시공전문가(업체상세페이지):두번째 탭 업체소개
	public CompanyInfo getCompanyIntroduction(Company com) {
		return companyInfoRepo.findById(Integer.parseInt(com.getId())).get();
	}

	
	
	/*
	 * //시공전문가(업체상세페이지):전문분야 public CompanySpecial getCompanySpecial(Company com) {
	 * CompanyInfo comi = new CompanyInfo(); comi.setCinfo_id(null);
	 * comi.setCinfo_id( Integer.parseInt( ));
	 * return companyInfoRepo.findBy(Integer.parseInt( com.getId())); }
	 */

	
	
	// 시공전문가(업체상세페이지):인테리어자랑
	public List<Brag> getInteriorBrag(Company com,Integer page) {
		int showCntPerPage = 3;
		Pageable pageable = (Pageable) PageRequest.of(page - 1, showCntPerPage, Sort.by("bdate").descending());
		return bragrepo.findByCompany(com, pageable);
	}

	   
	
	// 시공전문가(업체상세페이지):업체후기
	public List<Review> getCompanyReview(Company com, Integer page) {
		int showCntPerPage = 6;
		Pageable pageable = (Pageable) PageRequest.of(page - 1, showCntPerPage, Sort.by("rdate").descending());
		return reviewRepo.findByCompany(com, pageable);
	}
	
	
	//사업자 임시 비밀번호 발급
	public boolean companyEmailCheck(String email, String id) {
       Company com = companyRepo.findById(id).get();
        if(com!=null && com.getEmail().equals(email)) {
            return true;
        }
        else {
            return false;
        }
	}
	
	//사업자 아이디 찾기
	public Company companyNameTelCheck(String ceo,String tel){
		return companyRepo.findByCeoAndTel(ceo, tel);
      
	}

	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyPreferRepository companyPreferRepo;
	
	// 업체 북마크
	public void modifyBookMark(String memberId, String cid, boolean bookMark) {
		Member mem = memberRepo.findById(memberId).get();
		Company co = companyRepo.findById(cid).get();
		
		if(bookMark) {
			// true(있으면) 삭제
			CompanyPrefer prefer = companyPreferRepo.findByMemberAndCompany(mem, co);
			companyPreferRepo.delete(prefer);
			
		} else {
			// false(없으면) 생성
			CompanyPrefer prefer = new CompanyPrefer();
			prefer.setCompany(co);
			prefer.setMember(mem);
			companyPreferRepo.save(prefer);
			
		}
	}

}
