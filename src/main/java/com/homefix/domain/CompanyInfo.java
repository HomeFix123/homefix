package com.homefix.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "cp_info")
public class CompanyInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cinfo_id;
	private String cinfo_cont;
	private String cinfo_img;
	private String career;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "companyInfo")
	private List<CompanySpecial> specialty;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "companyInfo")
	private List<CompanyArea> area;
	
	@ManyToOne
	@JoinColumn(name = "cid")
	private Company company;
	
}
