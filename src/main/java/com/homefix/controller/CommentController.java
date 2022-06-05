package com.homefix.controller;

import java.util.List;

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
	public List<Comment> saveComment(String id,String content,Integer tid) {
		System.out.println("아이디 넘어온 거 " + id);
		logger.info("댓글 입력 성공");
		return commentService.saveComment(id, content, tid);
	}
	
	
}
