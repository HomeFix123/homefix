package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="contract")
@Table(name="contract")
public class Contract {
	
	/*계약[Contract] 테이블 */	
	@Id
	private Integer ctid; // 계약아이디
	private String ing;   // 진행도
	private Date ct_d;	  // 날짜
	
	@JoinColumn(name="eid")
	@OneToOne
	private Estimation estimation; //견적아이디(견적테이블)
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company compay;	// 업체 아이디(업체테이블) 
	

}
