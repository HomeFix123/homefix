package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
@Entity(name="contract")
@Table(name="contract")
@DynamicInsert /*(insert 시 null 인필드 제외)*/
@DynamicUpdate /* (update 시 null 인필드 제외) */
public class Contract {
	
	/*계약[Contract] 테이블 */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ctid; // 계약아이디
	private String ing;   // 진행도
	@Temporal(TemporalType.DATE)
	private Date ct_d = new Date();	  // 날짜
	
	@JoinColumn(name="eid")
	@OneToOne
	private Estimation estimation; //견적아이디(견적테이블)
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company company;	// 업체 아이디(업체테이블) 
	

}
