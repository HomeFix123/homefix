package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="chatting")
@Table(name="chatting")
public class Chatting {

	@Id
	private Integer room_id;
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;
	
	@JoinColumn(name="cid")
	@ManyToOne
	private Company compay;
	
}
