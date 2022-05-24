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

import lombok.Data;

@Data
@Entity(name = "cp_report")
@Table(name = "cp_report")
public class CompanyReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crpid")
	private Integer pid;
	
	@Column(name = "cp_reason")
	private String reason;
	
	@ManyToOne()
	@JoinColumn(name = "cid")
	private Company company;
	
	@ManyToOne()
	@JoinColumn(name = "id")
	private Member member;
	
	private Date rday;
}
