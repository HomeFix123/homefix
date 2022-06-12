package com.homefix.domain;



import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity(name = "brag")
@Data
@Table(name = "brag")
public class Brag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bid;
	
	@Column(name = "b_cont")
	private String  bcont;
	private String  btitle;
	private String  bimgadr;
	private Integer bcnt;
	private String  hometype;
	private Integer bsize;
	private Integer bbudget;
	private String  family;
	private String  job;
	private String  worker;
	private String  extrainfo;
	
	@Column(name = "b_color")
	private String  bcolor;
	private String  loc;
	private Date  bdate;
	
	@JoinColumn(name = "id")
	@ManyToOne
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "cid")
	private Company company;
	
	@OneToMany(mappedBy = "brag", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Prefer> preferList;
	
	@Transient
	private long prefer;
	@Transient
	private Boolean preferck;
	@Transient
	private Boolean reportck;
	
}
