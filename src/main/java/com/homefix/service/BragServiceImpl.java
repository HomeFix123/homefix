package com.homefix.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Contract;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;
import com.homefix.domain.Prefer;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.ContractRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.MemberReportRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PreferRepository;

@Service
public class BragServiceImpl implements BragService {
	
	@Autowired
	BragRepository bragRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	CompanyRepository companyRepo;

	@Autowired
	PreferRepository preferRepo;
	
	@Autowired
	MemberReportRepository memberRRepo;
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Override
	public void saveBrag(Brag brag, String cid, String id) {
		
		brag.setBcnt(0);
		brag.setMember(memberRepo.findById(id).get());
		brag.setCompany(companyRepo.findById(cid).get());
		brag.setBdate(new Date());
		
		System.out.println(brag);
		bragRepo.save(brag);
		
	}
	
	@Override
	public List<Brag> getBragList(Brag brag) {
		
		return (List<Brag>)bragRepo.findAll();
	}
	
	@Override
	public Brag getBrag(Brag brag, String id) {
		
		Brag result = bragRepo.findByBid(brag.getBid());
		result.setPrefer(preferRepo.countByBrag(brag));
		List<Prefer> list = preferRepo.findByBragAndMember(brag, memberRepo.findById(id).get());
		if(list.size() > 0) {
			result.setPreferck(true);
		} else {
			result.setPreferck(false);
		}

		return result;
		
	}
	
	
	@Override
	public void deleteBrag(Brag brag, String id) {
		Brag result = bragRepo.findByBid(brag.getBid());
		result.setMember(memberRepo.findById(id).get());
		bragRepo.delete(bragRepo.findByBidAndMember(result.getBid(), result.getMember()));
	}
	
	
	@Override
	public void savePrefer(Brag brag, String id) {
		
		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		System.out.println("좋아요 입력"+result);
		preferRepo.save(result);
		
	}
	
	@Override
	public void deletePrefer(Brag brag, String id) {
		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		preferRepo.deleteAll(preferRepo.findByBragAndMember(brag, result.getMember()));
	}
	
	
	@Override
	public String saveReport(Member id, String reporter, String reason) {
		Member repo = memberRepo.findById(reporter).get();
		List<MemberReport> list = memberRRepo.findByMemberAndReporter(id, repo);
		if(list.isEmpty()) {
			MemberReport result = new MemberReport();
			result.setMember(id);
			result.setReporter(repo);
			result.setReason(reason);
			memberRRepo.save(result);
			return "true";
			
		} else {
			
			return "false";
		
		}
	}
	
	
	@Override
	public Set<Company> getContractList(String id) { 
		Member mem = memberRepo.findById(id).get();
		List<Estimation> result = estRepo.findByMember(mem);
		Set<Company> con = new HashSet<>();
		for(int i=0; i < result.size(); i++) {
			Contract temp = contractRepo.findByEstimation(result.get(i));
			if(temp != null) {
			con.add(temp.getCompany());
			}
		}
		
		System.out.println("con"+con);
		return con; 
		
	}
	
}
