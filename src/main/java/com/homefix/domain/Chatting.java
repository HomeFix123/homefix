package com.homefix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity(name="chatting")
@Table(name="chatting")
public class Chatting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer room_id;
	
	private String nickname;
	
	@Column(name="cdate")
	@Temporal(TemporalType.DATE)
	private Date erdate = new Date();
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company company;
	
}
