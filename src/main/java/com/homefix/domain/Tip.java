package com.homefix.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name="tip")
@Table(name="tip")
public class Tip {
	
	/*인테리어팁[tip] 테이블 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tid; 	 // 팁 아이디
	private String category; // 카테고리(기본정보 선택)
	private String tiptitle; // 팁 제목
	private String tcontent; // 팁 내용
	
	@Transient
	private String tdate; 	// 날짜
	
	private String tipimg;   // 이미지 주소
	
	@JoinColumn(name="id")
	@ManyToOne
	private Member member;	// 이용자 아이디(멤버테이블)
	
	// 댓글 연관 관계
	@OneToMany(mappedBy = "tip")
	@JsonIgnore
	private List<Comment> CommentList;
	
	// 좋아요 연관 관계
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "tip")
	private List<Tip_prefer> tipPreferList; //좋아요 수
	
	// 팁 좋아요 용
	@Transient
	private long prefer; 
	@Transient
	private Boolean preferck;
	
	
}
