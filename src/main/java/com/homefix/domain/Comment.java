package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="comment")
@Table(name="comment")
public class Comment {
	
	/*댓글[comment] 테이블 */
	@Id
	private Integer cmid;   // 댓글 아이디
	private String content; // 댓글 내용
	private Date cdate; 	// 댓글 날짜
	
	@JoinColumn(name="tid")
	@ManyToOne
	private Tip tip;		// 팁 아이디(팁 테이블)
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;	// 이용자 아이디(멤버 테이블)
	

}
