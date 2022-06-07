package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "prefer")
public class Prefer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lid;
	
	@ManyToOne
	@JoinColumn(name = "bid")
	private Brag brag;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;
	
	
}
