package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Comment;
import com.homefix.domain.Tip;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
	
	public List<Comment> findByTip(Tip tip);
	
}
