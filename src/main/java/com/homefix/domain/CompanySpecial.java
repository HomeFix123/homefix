package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "specialty")
public class CompanySpecial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer spid;
	private String sp_cont;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cinfo_id")
	private CompanyInfo companyInfo;
}
