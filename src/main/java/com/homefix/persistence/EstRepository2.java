package com.homefix.persistence;

import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Estimation;

/* JPA Repository 란?
CRUD 기능을 처리하는 인터페이스로 기존의 DAO 역할로 DB 처리 */     //엔티티 클래스, @Id 매핑되는 변수의 타입
public interface EstRepository2 extends CrudRepository<Estimation, Integer>{


}
