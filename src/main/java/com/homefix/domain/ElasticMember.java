package com.homefix.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ElasticMember {

	String id;
	String nickname;
	String profileimg;
	String fav;
	
	Date birthday;
	Date subdate;
	
	
	
}
