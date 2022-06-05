package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity(name = "cp_area")
public class CompanyArea {
	@Id
	private Integer cp_arid;
	
	@JoinColumn(name = "at_id")
	@OneToOne
	private Area area;


}
