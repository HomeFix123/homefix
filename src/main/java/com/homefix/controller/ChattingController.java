package com.homefix.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Chatting;
import com.homefix.service.ChattingService;

@Controller
@RequestMapping("/chat")
public class ChattingController {
	
	@Autowired
	private ChattingService chatService;

	//업체에게 온 견적 상세보기에서 채팅하기 클릭 시 chat테이블에 값 저장
	@GetMapping("/getChatList")
	@ResponseBody
	public Map<String,Object> getChatList(HttpSession session , Model m) {
//		String sessionid = (String)session.getAttribute("id");
//		System.out.println("세션에 저장되어있는 아이디 : "+ sessionid);
//		m.addAttribute("chatRoom",chatService.getChatList(sessionid));
//		return chatService.getChatList(sessionid);
		
		String userId = (String)session.getAttribute("userId");
		String memberId = (String)session.getAttribute("memberId");
		System.out.println(userId);
		System.out.println(memberId);
		if(userId != null) {
			System.out.println("회사아이디임");
			Map <String,Object> map = new HashMap<>();
			map.put("user", "company");
			map.put("list", chatService.getUserChatList(userId));
			return map;
		}else if (memberId != null) {
			System.out.println("고객아이디임");
			Map <String,Object> map = new HashMap<>();
			map.put("member", "member");
			map.put("list", chatService.getMemberChatList(memberId));
			return map;
		}
		System.out.println("로그인해라");
		return null;
	}
}
