package com.homefix.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "brag")
@Data
@Table(name = "brag")
public class Brag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bid;
	private String  b_cont;
	private String  bimgadr;
	private Integer bcnt;
	private String  hometype;
	private Integer bsize;
	private Integer bbudget;
	private String  family;
	private String  job;
	private String  worker;
	private String  extrainfo;
	private String  b_color;
	private String  loc;
	
	@JoinColumn(name = "id")
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Member member;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "cid")
	private Company company;
	
}
