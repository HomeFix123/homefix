package com.homefix.service;

import com.homefix.domain.Comment;

public interface CommentService {
	
	// 댓글 입력하기
	public void saveComment(Comment comment, Integer tid);
	
	

}
