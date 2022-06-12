package com.homefix.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homefix.domain.Comment;
import com.homefix.domain.Member;
import com.homefix.service.CommentService;

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
	public Comment saveComment(String content,Integer tid, HttpSession session) {
		String id = (String)session.getAttribute("memberId");
		System.out.println("아이디 넘어온 거 " + id);
		logger.info("댓글 입력 성공");
		
		return commentService.saveComment(id, content, tid);
	}
	
	// 댓글 삭제
	@DeleteMapping("/delete/{cmid}")
	@ResponseBody
	public void deleteComment(Comment comment) {
		System.out.println("댓글아이디"+comment);
		commentService.deleteComment(comment);
		//return "댓글이 삭제되었습니다.";
	}
	
	
		
	
		
	
}
