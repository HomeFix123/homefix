package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "payment")
public class Payment {
	@Id
	private String pid;
	
	private Date pm_day;
	private Integer amount;
	private String pm_method;
	private String cid;
}
