package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Chatting;
import com.homefix.domain.Company;
import com.homefix.domain.Member;
import com.homefix.persistence.ChattingRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;


@Service
public class ChattingServiceImpl implements ChattingService {
	
	@Autowired
	ChattingRepository chatRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	MemberRepository memberRepo;

	@Override
	public List<Chatting> getUserChatList(String loginId) {
		Company company = companyRepo.findById(loginId).get();
		return chatRepo.findByCompany(company);
	}

	@Override
	public List<Chatting> getMemberChatList(String loginId) {
		Member member = memberRepo.findById(loginId).get();
		return chatRepo.findByMember(member);
	}

}
