package com.homefix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "area")
public class Area {
	@Id
	@Column(name = "at_id")
	private String id;
	private String aname;
	private String dt_area;
}
