package com.homefix.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="tip_prefer")
@Table(name="tip_prefer")
public class Tip_prefer {
	
	/* 팁 좋아요[tip-prefer] 테이블 */
	@Id
	private Integer tpid; // 팁좋아요 아이디
	
	@JoinColumn(name="tid")
	@ManyToOne
	private Tip tip;		// 팁 아이디(팁 테이블)
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;	// 이용자 아이디(멤버 테이블)
	
	
}
