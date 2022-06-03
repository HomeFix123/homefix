package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
}
