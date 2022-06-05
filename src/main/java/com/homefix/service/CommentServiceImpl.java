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
	public List<Comment> saveComment(String id,String content,Integer tid) {
		System.out.println("id!!!!!!!!!" + id);
		Tip tip = tipRepo.findById(tid).get();
		Member mem = memberRepo.findById(id).get();
		Comment comment = new Comment();
		comment.setTip(tip);
		comment.setMember(mem);
		comment.setContent(content);
		commentRepo.save(comment);
		
		//System.out.println("comment serviceImple에서 저장된 tid 값 확인 : ");
		Tip t = tipRepo.findById(tid).get();
		return commentRepo.findByTip(t);
	}	
	

	
	
}
