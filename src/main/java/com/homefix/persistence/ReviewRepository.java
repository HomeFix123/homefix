package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, String>{

}
