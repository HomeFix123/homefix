package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Chatting;
import com.homefix.domain.Company;
import com.homefix.domain.Contract;
import com.homefix.domain.Esti_request;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.persistence.ChattingRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.ContractRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.Esti_requestRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class EstServiceImpl implements EstService {
	
	@Autowired
	EstRepository estRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	Esti_requestRepository esti_reqRepo; 

	@Override
	public Estimation getEstDetail(String id) {
		Estimation resultData = estRepo.getEstDetail(id);
		System.out.println("resultData는"+resultData);
		return resultData;
	}

	@Override
	public List<Estimation> getCEsts(String cid, Integer page) {
		int showCntPerPage = 10;
	 	Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("eid").descending());
		
		Company com = companyRepo.findById(cid).get();
		List<Estimation> resultList = estRepo.findByCompany(com,pageable);
		for(Estimation result : resultList) {
			Contract contract =contractRepo.findByEstimation(result);
			if(contract != null) {
				result.setIng(contract.getIng());
				continue;
			}
			
			Esti_request estReq = esti_reqRepo.findByEstimationAndCompany(result, com);
			if(estReq != null) {
				result.setIng("요청중");
			}
			
		}
		return resultList;
	}
	
	//페이징 
	@Override
	public long countCEst(String cid) {
		Company company = companyRepo.findById(cid).get();
		int showCntPerPage = 10;
		return (long)(estRepo.countByCompany(company)-1)/showCntPerPage + 1;
	}
	
	

	
	@Override
	public List<Estimation> getCEst(String cid, Integer page) {
		Company com = companyRepo.findById(cid).get();
		
		int showCntPerPage = 6; 
		  
		  Pageable pageable = (Pageable) PageRequest.of(page - 1, showCntPerPage,
		  Sort.by("startDay"));
		 
		
		return estRepo.findByCompany(com, pageable);
	}

	
	
	
	@Override
	public List<Estimation> getMEstimation(String id,Integer page) {
		int showCntPerPage = 10;
		Pageable pageable = PageRequest.of(page-1, showCntPerPage, Sort.by("eid").descending());
		
		Member member = memberRepo.findById(id).get();
		System.out.println("resultData는"+ estRepo.findByMember(member));
		List<Estimation> resultList = estRepo.findByMember(member,pageable);
		for(Estimation result: resultList) {
			Contract contract =contractRepo.findByEstimation(result);
			if(contract != null) {
				result.setIng(contract.getIng());
			}
		}
		return resultList;
	}
	
	//페이징
	@Override
	public long countMEList(String id) {
		Member member = memberRepo.findById(id).get();
		int showCntPerPage = 10;
		return (long)(estRepo.countByMember(member)-1)/showCntPerPage + 1;
	}

	//내(고객) 견적 리스트 상세보기
	@Override
	public Estimation getMEDetail(Integer id) {
		return estRepo.findById(id).get();
	}

	@Override
	public List<Esti_request> getMEDetailC(Integer id) {
		Estimation estimation = estRepo.findById(id).get();
		return esti_reqRepo.findByEstimation(estimation);
	}

	//업체가 진행중인 견적 리스트 보기
	@Override 
	public Page<Contract> getCIngList(String cid, String situation, Integer page) {
		Company company  = companyRepo.findById(cid).get();
		int showCntPerPage = 5;
		Pageable pageable = PageRequest.of(page -1, showCntPerPage, Sort.by("ctid").descending());
		if(situation == null || situation.equals("")) {
			
			return contractRepo.findByCompany(company, pageable);
		}
		
		if(situation.equals("doing")) {
			
			return contractRepo.findByCompanyAndIng(company, "진행중", pageable);
		}
		
		if(situation.equals("done")) {
			
			return contractRepo.findByCompanyAndIng(company, "시공완료", pageable);
		}
		return contractRepo.findByCompany(company, pageable);
	}

	@Override
	public void saveIng(Integer eid) {
		Estimation estimation = estRepo.findById(eid).get();
	 	Contract contract = contractRepo.findByEstimation(estimation);
		contract.setIng("시공완료"); 
		contractRepo.save(contract);
	}

	//업체에게 온 견적 상세보기에서 확정하기 클릭 시  esti_request테이블에 값 저장
	@Override
	public void saveEstReq(Integer eid, String cid) {
		Estimation estimation = estRepo.findById(eid).get();
		Company company  = companyRepo.findById(cid).get();
		Esti_request estReq = new Esti_request();
		estReq.setEstimation(estimation);
		estReq.setCompany(company);
		esti_reqRepo.save(estReq);
	}

	@Autowired
	ChattingRepository chatRepo;
	
	//업체에게 온 견적 상세보기에서 채팅하기 클릭 시 chat테이블에 값 저장
	@Override
	public Chatting saveChatRoom(String id, String cid, String nickname) {
		
		Member member = memberRepo.findById(id).get();
		Company company  = companyRepo.findById(cid).get();
		Chatting chat = chatRepo.findByMemberAndCompany(member, company);
		
		//DB에 방이 있을 경우
		if(chat != null) {
			System.out.println("값이 존재합니다.");
			return chat; 
		}
		//DB에 방이 없을 경우 입력 후 검색 
		System.out.println("값이 null입니다");
		Chatting chatting = new Chatting();
		chatting.setMember(member);
		chatting.setCompany(company);
		chatting.setNickname(nickname);
		chatRepo.save(chatting);
		System.out.println("값을 입력했습니다.");
		Chatting newChat = chatRepo.findByMemberAndCompany(member, company);
		System.out.println("입력한 값을 찾아왔습니다.");
		return newChat;
		
	}

	//(회사) 확정요청하기/확정요청완료/확정요청불가 유무
	@Override
	public String getEstiReq(Integer eid, String cid) {
		//cid = "1";
		//cid = "1426";
		//cid = "905";
		//eid=38;
		Estimation estimation = estRepo.findById(eid).get();
		Company company  = companyRepo.findById(cid).get();
		Esti_request estiReq = esti_reqRepo.findByEstimationAndCompany(estimation, company);
		List<Esti_request> estiReqEid = esti_reqRepo.findByEstimation(estimation);
		Contract contract = contractRepo.findByEstimation(estimation);
		//계약테이블에 견적id eid있으면 확정요청불가 버튼
		if(contract != null) {
			System.out.println("계약테이블에서 이미 진행중임");
			return "확정요청불가";
			
		//계약테이블에 eid 없고 견적요청 테이블에 cid와 eid 둘다 있을경우 확정요청완료
		}else if(estiReq != null) {
			System.out.println("!!!!견적 요청 테이블에 데이터 있음");
			return "확정요청완료";
			
		//계약테이블에 eid 없고 견적요청 테이블에 eid만 있을경우 등등등 확정요청하기
		}else {
			System.out.println("확정요청하기 가능");
			return "확정요청하기";
		}
	}

	//(고객) 내 견적 상세보기에서 확정버튼 클릭하면 contract db에 값 저장
	@Override
	public void saveContract(Integer eid, String cid) {
		Estimation esti = estRepo.findById(eid).get();
		Company company = companyRepo.findById(cid).get();
		Contract contract = new Contract();
		contract.setEstimation(esti);
		contract.setCompany(company);
		contractRepo.save(contract);
	}

	@Override
	public Contract checkContract(Integer eid) {
		Estimation esti = estRepo.findById(eid).get();
		return contractRepo.findByEstimation(esti);
	}

}
