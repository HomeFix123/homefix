package com.homefix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity(name="esti_request")
@Table(name="esti_request")
public class Esti_request {
	
	/* 견적요청[esti_request] 테이블 */	
	@Id
	private Integer erid; // 요청 아이디
	
	@Column(name="erdate")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date erdate = new Date();
	
	@JoinColumn(name="eid")
	@ManyToOne
	private Estimation estimation;	// 견적 아이디(견적테이블)
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company company;	// 업체 아이디(업체테이블) 
	

}
