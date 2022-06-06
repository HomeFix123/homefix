package com.homefix.service;

import java.util.HashMap;
import java.util.List;

import com.homefix.domain.Area;
import com.homefix.domain.CompanyInfo;

public interface CompanyWriteService {
	
	List<String> getAreaNameList();
	
	HashMap<String, List<Area>> getAreaList(List<String> areaNameList);
	
	void insertCompanyInfo(String companyId, CompanyInfo companyInfo, String[] specialtyArr, String[] spacesArr);
	
	CompanyInfo getCompanyInfo(String companyId);
}
