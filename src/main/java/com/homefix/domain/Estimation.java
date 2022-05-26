package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="estimation")
@Table(name="estimation")
public class Estimation {
	/*견적 Estimation 테이블*/
	
	@Id
	private Integer eid;	 	// 견적 아이디
	
	private String space;		// 공간유형
	private String building;	// 건물유형
	private Integer size;		// 평수
	private Integer budget;		// 예산
	private Date start_d;		// 시작 희망일
	private Date end_d;			// 종료 희망일
	private String eaddr;		// 간단 주소
	private String ename;		// 이름
	private String etel;		// 전화번호
	private String estyle;		// 선호스타일
		
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;		// 이용자 아이디
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company company;		// 업체 아이디
	
}
