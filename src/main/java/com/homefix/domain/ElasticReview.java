package com.homefix.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ElasticReview {
	String id;
	String title;
	String content;
	String image;
	
	Date date;
	
	String coid;
	String logo;
	String name;
	Date deadday;
	
	String color;
	
	String hometype;
	String loc;
	String job;
	String family;
	String worker;
	String extrainfo;

	Integer budget;
	Integer size;
	Integer cnt;
	
}
