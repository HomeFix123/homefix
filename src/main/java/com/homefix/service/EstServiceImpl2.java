package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.Estimation;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class EstServiceImpl2 implements EstService2 {
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyRepository companyRepo;

	@Override
	public void saveEst(Estimation est, String id) {
		est.setMember(memberRepo.findById(id).get());
		estRepo.save(est);
		System.out.println("입력값 확인 " + estRepo.save(est));
	}

	// 전체견적 리스트
	@Override
	public List<Estimation> getEstList(Estimation est, int page) {
		//return (List<Estimation>)estRepo.findAll();
		int showCntPerPage = 10;
		Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("eid").descending());
		return estRepo.findByCompanyNull(pageable);
	}
	
	// 전체견적 개수 (페이징 용)
	@Override
	public long countEstList() {
		int showCntPerPage = 10;
		return (long)(estRepo.count()-1)/showCntPerPage + 1;
	}
	
	// 전체 견적 목록 상세보기
	@Override
	public Estimation getEstDetails(Integer eid) {
		System.out.println("서비스에서 가져온 아이디 " + eid);
		return estRepo.findById(eid).get(); //EstRepository에 있는 getEstDetail함수 리턴
		
	}

	

	



	
	
	
	
	 
	
	
	
}
