package com.homefix.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Tip;

public interface TipRepository extends CrudRepository<Tip, Integer>{
	
	public List<Tip> findAll(Pageable pageabe);
}
