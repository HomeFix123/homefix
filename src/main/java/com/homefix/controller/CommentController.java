package com.homefix.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Comment;
import com.homefix.service.CommentService;
import com.homefix.service.CommentServiceImpl;

import io.netty.handler.codec.http.HttpRequest;

@Controller
@RequestMapping("/tip")
public class CommentController {
	
	static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	// 댓글 목록
	public void getCmList(Model m) {
		Comment comment = new Comment();
		m.addAttribute("commentList", commentService.getCmList(comment));
	}
	
	// 댓글 저장
	@RequestMapping("/save")
	@ResponseBody//에이작스 뷰페이지 변경하지 않게 붙여줌
	public String saveComment(String id,String content,Integer tid) {
		System.out.println("아이디 넘어온 거 " + id);
		logger.info("댓글 입력 성공");
		commentService.saveComment(id, content, tid);
		//return commentService.getNickname(id).getNickname(); //멤버 아이디에 따른 레코드를 검색하고 그 안에서 닉네임 뽑아오기 => 에이작스로 리턴
		return "<img src='http://140.238.11.118:5000/upload/"+ commentService.getProfilimg(id).getProfilimg() + "' width='20px'> " + commentService.getNickname(id).getNickname();
	}
	
	
}
