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

	@Id
	private Integer eid;
	
	private String space;
	private String building;
	private Integer size;
	private Integer budget;
	private Date start_d;
	private Date end_d;
	private String ename;
	private String etel;
	private String estyle;
	private String eaddr;
	
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company compay;
	
}
