package com.homefix.service;

import java.util.List;

import com.homefix.domain.Comment;

public interface CommentService {
	
	// 댓글 입력하기
	public List<Comment> saveComment(String member,String content,Integer tid);
	
	// 댓글 목록
	public List<Comment> getCmList(Comment comment);

}
