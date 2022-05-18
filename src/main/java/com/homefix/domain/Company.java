package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "company")
@Table(name = "company")
public class Company {
	@Id
	private String cid;
	private String co_name;
	private String co_pass;
	private String co_addr;
	private String co_zip;
	private String co_addr_d;
	private String co_num;
	private String co_tel;
	private String co_email;
	private String co_ceo;
	private String co_logo;
	private Integer co_cnt;
	
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
