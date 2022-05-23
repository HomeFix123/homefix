package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="tip")
@Table(name="tip")
public class Tip {
	
	/*인테리어팁[tip] 테이블 */
	@Id
	private Integer tid; 	 // 팁 아이디
	private String category; // 카테고리(기본정보 선택)
	private String tiptitle; // 팁 제목
	private String tcontent; // 팁 내용
	private Date tdate; 	// 날짜
	private String tipimg;   // 이미지 주소
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;	// 이용자 아이디(멤버테이블)
	
}
