package com.homefix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "cp_prefer")
public class CompanyPrefer {
	@Id
	@Column(name = "clid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cid")
	private Company company;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;
	
}
