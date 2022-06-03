package com.homefix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Comment;
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
	
	// 댓글 입력
	@Override
	public void saveComment(Comment comment, Integer tid) {
		comment.setTip(tipRepo.findById(tid).get());
		System.out.println("comment serviceImple에서 저장된 tid 값 확인 : " + comment);
		commentRepo.save(comment);
		System.out.println("comment serviceImple 입력값 확인 : " + commentRepo.save(comment));
		
	}	
	

	
	
}
