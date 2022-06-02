package com.homefix.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity(name = "payment")
@Table(name = "payment")
public class Payment {
	@Id
	private String pid;

	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy. MM. dd.")
	@Column(name = "pm_day")
	private Date pday;
	private Integer amount;
	@Column(name = "pm_method")
	private String method;

	@JoinColumn(name = "cid")
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Company company;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy. MM. dd.")
	@Column(name = "pm_last")
	private Date plast;

}
