package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "cp_area")
public class CompanyArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cp_arid;
	
	@JoinColumn(name = "at_id")
	@OneToOne
	private Area area;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cinfo_id")
	private CompanyInfo companyInfo;

}
