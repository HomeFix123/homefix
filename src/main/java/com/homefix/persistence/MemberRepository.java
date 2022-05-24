package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String>{

	void deleteAllById(Member mem);

	List<Company> findByIdAndPassword(String id, String password);

	int countById(Member mem);

	List<Company> findByEmail(String email);

}
