package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Brag;
import com.homefix.domain.Member;
import com.homefix.domain.Prefer;

public interface PreferRepository extends CrudRepository<Prefer, Integer> {

	long countByBrag(Brag brag);

	Boolean findByBragAndMember(Brag brag, Member member);

}
