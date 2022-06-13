package com.homefix.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;
import com.homefix.domain.Payment;
import com.homefix.domain.Tip;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.CompanyInfoRepository;
import com.homefix.persistence.CompanyReportRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.DashboardRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.MemberReportRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PaymentRepository;
import com.homefix.persistence.TipRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	MemberRepository memberRepo;
	
	/*
	 * 고객 전체 목록 불러오기
	 */
	@Override
	public List<Member> getMemberList(){
		
		return (List<Member>)memberRepo.findAll();
	}
	
	/*
	 * 고객 아이디로 고객 정보 가져오기
	 */
	@Override
	public Member getMember(String id) {
		return memberRepo.findById(id).get();
	}

	/*
	 * 고객 정보 수정
	 */
	@Override
	public void updateMember(Member member) {
		Member temp = memberRepo.findById(member.getId()).get();
		System.out.println(temp);
		temp.setEmail(member.getEmail());
		temp.setNickname(member.getNickname());
		temp.setProfilimg(member.getProfilimg());
		
		memberRepo.save(temp);
		
	}

	/*
	 * 고객 정보 수정
	 */
	@Override
	public void enableBlackMember(String id, Boolean enabled) {
		Member temp = memberRepo.findById(id).get();
		temp.setEnabled(enabled);
		
		memberRepo.save(temp);
	}
	
	@Autowired
	MemberReportRepository memberReportRepo;
	
	/*
	 * 고객 신고 목록 불러오기
	 */
	@Override
	public List<MemberReport> getMemberReportList() {
		
		return (List<MemberReport>)memberReportRepo.findAll();
	}

	/*
	 * 고객 신고 삭제
	 */
	@Override
	public void deleteMemberReport(String rid) {
		Integer id = Integer.parseInt(rid);
		MemberReport memRe = memberReportRepo.findById(id).get();
		memberReportRepo.delete(memRe);
	}
	
	/*
	 * 오늘의 고객 신고 개수
	 */
	@Override
	public Long countTodayMemberReport() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1); // 빼고 싶다면 음수 입력
		Date date = new Date(cal.getTimeInMillis());
		return memberReportRepo.countByRdayGreaterThan(date);
	}
	
	
	@Override
	public List<MemberReport> getMemberReport(String id) {
		Member temp = memberRepo.findById(id).get();
		
		return memberReportRepo.findByMember(temp);
	}
	
	
	
	@Autowired
	CompanyRepository companyRepo;
	
	/*
	 * 업체 전체 목록 불러오기
	 */
	@Override
	public List<Company> getCompanyList() {
		List<Map<String, Object>> lst = companyRepo.findAllForAdmin();
		List<Company> resultList = new ArrayList<>();
		for(Map<String, Object> temp : lst) {
			Company result = new Company();
			result.setId((String)temp.get("id")); 
			result.setName((String)temp.get("name"));
			result.setChatting(Integer.parseInt(String.valueOf(temp.get("chatting"))));
			result.setContract(Integer.parseInt(String.valueOf(temp.get("contract"))));
			result.setReport(Integer.parseInt(String.valueOf(temp.get("report"))));
			if(temp.get("pm_day") != null) {
				result.setPm_day(Integer.parseInt(String.valueOf(temp.get("pm_day"))));
			}
			
			resultList.add(result);
		}
		return resultList;
	}
	
	/*
	 * 업체 아이디로 정보 불러오기
	 */
	@Override
	public Company getCompany(String cid) {
		
		return companyRepo.findById(cid).get();
	}
	
	/*
	 * 업체 정보 수정
	 */
	@Override
	public void updateCompany(Company company) {
		
		Company temp = companyRepo.findById(company.getId()).get();
		temp.setLogo(company.getLogo());
		temp.setName(company.getName());
		temp.setEmail(company.getEmail());
		
		companyRepo.save(temp);
	}
	
	/*
	 * 업체 블랙리스트 지정/해제
	 */
	@Override
	public void enableBlacklist(String cid, Boolean enabled) {
		Company company = companyRepo.findById(cid).get();
		company.setEnabled(enabled);
		companyRepo.save(company);
		
	}
	
	
	@Autowired
	CompanyInfoRepository companyDetailRepo;
	
	/*
	 * 업체 상세정보 불러오기
	 */
	@Override
	public CompanyInfo getCompanyDetail(String cid) {
		Company co = companyRepo.findById(cid).get();
		List<CompanyInfo> result = companyDetailRepo.findByCompany(co);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	
	@Autowired
	CompanyReportRepository companyReportRepo;
	/*
	 * 업체 신고 목록 불러오기
	 */
	@Override
	public List<CompanyReport> getCompanyReportList() {
		
		return (List<CompanyReport>) companyReportRepo.findAll();
	}
	
	/*
	 * 업체 신고 삭제
	 */
	@Override
	public void deleteCompanyReport(String rid) {
		Integer id = Integer.parseInt(rid);
		CompanyReport report = companyReportRepo.findById(id).get();
		companyReportRepo.delete(report);
	}
	
	@Autowired
	PaymentRepository paymentRepo;
	/*
	 * 결제정보 리스트 불러오기
	 */
	@Override
	public List<Payment> getPaymentList(String cid) {
		Company temp = companyRepo.findById(cid).get();
		return paymentRepo.findByCompany(temp);
	}
	
	@Override
	public long countTodayCompanyReport() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1); // 빼고 싶다면 음수 입력
		Date date = new Date(cal.getTimeInMillis());
		return companyReportRepo.countByRdayGreaterThan(date);
	}
	
	/*
	 * 업체 신고 내역
	 */
	@Override
	public List<CompanyReport> getCompanyReport(String id) {
		Company company = companyRepo.findById(id).get();
		return companyReportRepo.findByCompany(company);
	}

	
	@Autowired
	BragRepository bragRepo;
	
	/*
	 * 자랑글 목록 
	 */
	@Override
	public List<Brag> getBragList(int page) {
		int showCntPerPage = 5;
		Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("bid").descending());
		
		return bragRepo.findAll(pageable);
	}

	/*
	 * 자랑글 개수(페이징용)
	 */
	@Override
	public long countBragList() {
		int showCntPerPage = 5;
		return (long)(bragRepo.count()+1)/showCntPerPage + 1;
	}
	
	
	@Autowired
	TipRepository tipRepo;
	
	/*
	 * 팁 게시글 목록
	 */
	@Override
	public List<Tip> getTipList(int page) {
		// Tip Repo가 완성되면 진행
		int showCntPerPage = 5;
		
		Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("tid").descending());
		
		return tipRepo.findAll(pageable);
	}

	/*
	 * 팁 게시글 개수
	 * 페이징용
	 */
	@Override
	public long countTipList() {
		int showCntPerPage = 5;
		return (tipRepo.count()+1)/showCntPerPage + 1;
	}
	
	
	@Autowired
	DashboardRepository dashRepo;

	/*
	 * 회원가입수 집계
	 */
	@Override
	public List<Object[]> aggregateNewUser(){
		
		return dashRepo.searchAggNewUser();
	}
	/*
	 * 결제 집계
	 */
	@Override
	public List<Object[]> aggregatePayments(){
		
		return dashRepo.searchAggPayments();
	}

	@Autowired
	EstRepository estRepo;
	
	/*
	 * 고객이 작성한 견적 목록
	 */
	@Override
	public List<Estimation> getEstimationList(String id) {
		Member mem = memberRepo.findById(id).get();
		return estRepo.findByMember(mem);
	}

	@Override
	public long countNewUser() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_WEEK, -1); // 빼고 싶다면 음수 입력
		Date date = new Date(cal.getTimeInMillis());
		
		return memberRepo.countBySubdateGreaterThan(date);
	}

	@Override
	public long countPayUser() {
		
		return paymentRepo.countByPlastGreaterThan(new Date());
	}

	
	@Override
	public long countContractMonth() {
		return dashRepo.countContractMonth();
	}
	
	

}
