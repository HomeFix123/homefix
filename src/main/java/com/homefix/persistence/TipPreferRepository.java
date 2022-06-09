package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;
import com.homefix.domain.Tip;
import com.homefix.domain.Tip_prefer;


public interface TipPreferRepository extends CrudRepository<Tip_prefer, Integer> {
	
	// 좋아요 개수
	long countByTip(Tip tip);
	
	List<Tip_prefer> findByTipAndMember(Tip tip, Member member); //좋아요 한것을 가져옴

	void deleteByTipAndMember(Tip tip, Member member);

	
	
	

}
