package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "specialty")
public class CompanySpecial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer spid;
	private String sp_cont;
}
