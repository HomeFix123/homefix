package com.homefix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity(name = "company")
@Table(name = "company")
public class Company {
	
	@Id
	@Column(name="cid")
	private String id;
	@Column(name="co_name")
	private String name;
	@Column(name="co_pass")
	private String pass;
	@Column(name="co_addr")
	private String addr;
	@Column(name="co_zip")
	private String zip;
	@Column(name="co_addr_d")
	private String addrd;
	@Column(name="co_num",unique=true)
	private String num;
	@Column(name="co_tel")
	private String tel;
	@Column(name="co_email")
	private String email;
	@Column(name="co_ceo")
	private String ceo;
	@Column(name="co_logo")
	private String logo;
	@Column(name="co_cnt")
	private Integer cnt;
	
	private Boolean enabled;

	@Transient
	private int chatting;
	@Transient
	private int contract;
	@Transient
	private int report;
	@Transient
	private Date pm_day = new Date();
	@Transient
	private int prefer;

}
