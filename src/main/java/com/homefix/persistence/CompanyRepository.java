package com.homefix.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyDto;


public interface CompanyRepository extends CrudRepository<Company, String>{

	public Long countById(String id);
	
	public List<Company> findByEmail(String email) ;
	
	public Long countByNum(String num);
	
	public List<Company> findByIdAndPass(String Id, String pass);

	public void save(CompanyDto dto);
	
	
	
}
