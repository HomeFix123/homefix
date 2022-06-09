package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	public List<Review> findAll(Pageable pageable);

	public Review findByRidAndCompany(Integer rid, Company company);

		public List<Review> findByCompany(Company com, Pageable pageable);


 

}
