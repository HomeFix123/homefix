package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, String>{

}
