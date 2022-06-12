package com.homefix.service;

import java.util.List;

import com.homefix.domain.Comment;
import com.homefix.domain.Member;

public interface CommentService {
	
	// 댓글 입력하기
	public Comment saveComment(String member,String content,Integer tid);
	
	public void deleteComment(Comment comment);
	
	// 댓글 목록
	public List<Comment> getCmList(Comment comment);
	
	// 사용자 닉네임 구하기
	public Member getNickname(String id);
	
	// 사용자 프로필이미지 구하기
	public Member getProfilimg(String img);

}
