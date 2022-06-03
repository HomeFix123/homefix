package com.homefix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.homefix.domain.Comment;
import com.homefix.service.CommentService;

@Controller
@RequestMapping("/tip")
public class CommentController {
	
	static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/save")
	public String saveComment(Comment comment, Integer tid) {
		commentService.saveComment(comment, tid);
		logger.info("댓글 입력 성공");
		return null;
	}
	
	
}
