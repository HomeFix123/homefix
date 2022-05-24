package com.homefix.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "member_report")
@Table(name = "member_report")
public class MemberReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rpid")
	private Integer rpid;
	
	@Column(name = "m_reason")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "reporter")
	private Member reporter;
	
	private Date rday;
}
