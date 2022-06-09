package com.homefix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
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
	
	@Column(name = "addr_d")
	private String addrd;
	
	private String email;
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	@Temporal(TemporalType.DATE)
	private Date subdate;
	private String fav;
	private Date bday;
	private String profilimg;
	
	@Enumerated(EnumType.STRING) //EnumType.STRING 로 지정했기때문에 권한에 해당하는 값이 문자열로 저장됨!
	private Role role;
	private boolean enabled;

	@JsonIgnore
	private String kakao;
	
	
	
	
}
