package com.homefix.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "member")
@Data
@Table(name = "member")
public class Member {
	
	@Id
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String tel;
	private String zipcode;
	private String addr;
	private String addr_d;
	private String email;
	private Date subdate;
	private String fav;
	private Date bday;
	private String profilimg;
	private Boolean enabled;
	
	
	
}
