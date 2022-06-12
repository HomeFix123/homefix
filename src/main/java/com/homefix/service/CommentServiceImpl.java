package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Comment;
import com.homefix.domain.Member;
import com.homefix.domain.Tip;
import com.homefix.persistence.CommentRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.TipRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	TipRepository tipRepo;
	
	// 댓글 리스트
	public List<Comment> getCmList(Comment comment){
		return (List<Comment>) commentRepo.findAll();
	}
		
	// 댓글 입력
	@Override
	public Comment saveComment(String id,String content,Integer tid) {
		System.out.println("id!!!!!!!!!" + id);
		Tip tip = tipRepo.findById(tid).get();
		Member mem = memberRepo.findById(id).get();
		Comment comment = new Comment();
		comment.setTip(tip);
		comment.setMember(mem);
		comment.setContent(content);
		Comment result = commentRepo.save(comment);
		
		
		return result; 
	}
	
	// 댓글 삭제하기
	@Override
	public void deleteComment(Comment comment) {
	Comment	com = commentRepo.findById(comment.getCmid()).get();
		commentRepo.delete(com);
	}	
	
	// 닉네임 구하기
	@Override
	public Member getNickname(String id) {
		return memberRepo.findById(id).get();
	}
	
	// 
	@Override
	public Member getProfilimg(String img) {
		return memberRepo.findById(img).get();
	}

	
}
