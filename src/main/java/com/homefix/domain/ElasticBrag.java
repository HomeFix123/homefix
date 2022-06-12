package com.homefix.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
public class ElasticBrag {

	String id;
	String title;
	String content;
	String loc;
	String job;
	String hometype;
	String extrainfo;
	String family;
	String profileimg;
	String color;
	String mainimg;
	
	Integer cnt;
	Integer prefer;
	Integer size;
	Integer budget;
	
	Date date;
	
	Set<String> preferids;
	
	Boolean preferck;
	
	ElasticMember member;
	
	
	
}
