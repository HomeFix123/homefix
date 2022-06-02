package com.homefix.service;

import java.util.List;

import com.homefix.domain.Contract;
import com.homefix.domain.Esti_request;
import com.homefix.domain.Estimation;

public interface EstService {
	
	//견적 상세목록 불러오기
	public Estimation getEstDetail(String id);
	
	public List<Estimation> getCEst(String id);
	
	public List<Estimation> getMEstimation(String id);
	
	//내(고객) 견적 리스트 상세보기
	public Estimation getMEDetail(Integer id);
	
	//내(고객)리스트 상세보기 회사리스트 보기
	public List<Esti_request> getMEDetailC(Integer id);
	
	//업체가 진행중인 견적 리스트 보기
	public List<Contract> getCIngList(String eid);

	//진행중 -> 시공완료
	public void saveIng(Integer id);
	
	//(회사) 확정하기 선택 시 esti_request테이블에 값 저장
	public void saveEstReq(Integer eid,String cid);
}
