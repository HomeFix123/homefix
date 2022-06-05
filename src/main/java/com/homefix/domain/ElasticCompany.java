package com.homefix.domain;

import java.util.Set;

import lombok.Data;
@Data
public class ElasticCompany {
	String id;
	String logo;
	String name;
	String content;
	String img;
	String addr;
	Integer enabled;

	Set<String> special;
	Set<String> spaces;
	
	Integer career;
	Integer prefer;
	Integer contract;
	Integer review;
	
	Boolean isPrefer = false;
}
