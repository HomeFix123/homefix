package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, String>{
	
	public List<Review> findAll(Pageable pageable);
	
	public Review findByCompany(Company com);
}
