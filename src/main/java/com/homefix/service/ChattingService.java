package com.homefix.service;

import java.util.List;

import com.homefix.domain.Chatting;

public interface ChattingService {
	public List<Chatting> getUserChatList(String loginId);
	
	public List<Chatting> getMemberChatList(String loginId);
}
