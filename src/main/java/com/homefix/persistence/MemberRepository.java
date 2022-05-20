package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{

}
