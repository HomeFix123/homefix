package com.homefix.persistence;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.Estimation;

/* JPA Repository 란?
CRUD 기능을 처리하는 인터페이스로 기존의 DAO 역할로 DB 처리 */     //엔티티 클래스, @Id 매핑되는 변수의 타입
public interface EstRepository extends CrudRepository<Estimation, Integer>{
	
	public List<Estimation> findByCompany(Company company);

    @Query(value="SELECT building,size,budget,eaddr,ename,eid FROM estimation WHERE cid = ?1" , nativeQuery = true)
    List<HashMap<String, Object>> queryAnnotation(String word);

    @Query(value="SELECT * FROM estimation WHERE eid = ?1" , nativeQuery = true)
    Estimation getEstDetail(String word);
	

}
