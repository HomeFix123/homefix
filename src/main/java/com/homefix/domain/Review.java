package com.homefix.domain;



import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertFalse;

import lombok.Data;

@Entity(name = "review")
@Data
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rid;
	
	@Column(name = "r_cont")
	private String  rcont;
	private String  rtitle;
	private String  rimgadr;
	private Integer rcnt;
	private String  hometype;
	private Integer rsize;
	private Integer rbudget;
	private String  family;
	private String  job;
	private String  worker;
	private String  extrainfo;
	
	@Column(name = "r_color")
	private String  rcolor;
	private String  loc;
	@Column(name = "r_date")
	private Date  rdate;
	
	@ManyToOne
	@JoinColumn(name = "cid")
	private Company company;
	
	
	
	
}
