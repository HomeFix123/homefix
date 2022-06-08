package com.homefix.persistence;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;

/* JPA Repository 란?
CRUD 기능을 처리하는 인터페이스로 기존의 DAO 역할로 DB 처리 */     //엔티티 클래스, @Id 매핑되는 변수의 타입
public interface EstRepository extends CrudRepository<Estimation, Integer>{
	
	public List<Estimation> findByCompany(Company company);
	public List<Estimation> findByCompany(Company company, Pageable pageable);
	
	//페이징 오버로딩
	public List<Estimation> findByCompany(Company company,Pageable pageable);
	
	public List<Estimation> findByMember(Member member);
	
	//페이징 오버로딩
	public List<Estimation> findByMember(Member member,Pageable pageable);
	
	@Query(value="SELECT building,size,budget,eaddr,ename,eid FROM estimation WHERE cid = ?1" , nativeQuery = true)
	List<HashMap<String, Object>> queryAnnotation(String word);
	
	@Query(value="SELECT * FROM estimation WHERE eid = ?1" , nativeQuery = true)
	Estimation getEstDetail(String word);
		
	//전체 견적에서 cid 지정 안된 견적리스트 가져오기
	@Query(value="SELECT * FROM estimation WHERE cid IS NULL" , nativeQuery = true)
	List<Estimation> findByCompanyNull(Pageable pageable);
	
	// 전체 견적 페이징 처리
	public List<Estimation> findAll(Pageable pageable);
	
	public long countByMember(Member member);
	
	public long countByCompany(Company company);

}
