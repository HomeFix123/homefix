package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "area")
public class Area {
	@Id
	private String at_id;
	private String aname;
	private String dt_area;
}
