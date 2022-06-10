package com.homefix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homefix.domain.Area;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyArea;
import com.homefix.domain.CompanyInfo;
import com.homefix.domain.CompanySpecial;
import com.homefix.persistence.AreaRepository;
import com.homefix.persistence.CompanyAreaRepository;
import com.homefix.persistence.CompanyInfoRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.CompanySpecialRepository;

@Service
public class CompanyWriteServiceImpl implements CompanyWriteService {

	@Autowired
	AreaRepository areaRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	CompanyInfoRepository companyInfoRepo;
	
	@Autowired
	CompanyAreaRepository companyAreaRepo;
	
	@Autowired
	CompanySpecialRepository companySpecialRepo;
	
	
	@Override
	public List<String> getAreaNameList() {
		return areaRepo.findAname();
	}
	
	
	@Override
	public HashMap<String,List<Area>> getAreaList(List<String> areaNameList) {
		
		HashMap<String, List<Area>> result = new HashMap<>();
		for(String aname : areaNameList) {
			List<Area> areaList = (ArrayList<Area>) areaRepo.findByAnameOrderById(aname);
				
			result.put(aname, areaList);
		}
		
		return result;
	}


	@Override
	public CompanyInfo insertCompanyInfo(String companyId, CompanyInfo companyInfo, String[] specialtyArr,
			String[] spacesArr) {
		Company company = companyRepo.findById(companyId).get();
		
		companyInfo.setCompany(company);
		Integer infoId = companyInfo.getCinfo_id();
		
		if(infoId != null) {
			CompanyInfo temp = companyInfoRepo.findById(infoId).get();
			companyAreaRepo.deleteAllByCompanyInfo(temp);
			companySpecialRepo.deleteAllByCompanyInfo(temp);
		}
		
		companyInfo = companyInfoRepo.save(companyInfo);
		
		if(specialtyArr != null && specialtyArr.length > 0) {
			
			for(String special : specialtyArr) {
				if(special != null && !special.equals("")) {
					CompanySpecial companySpecial = new CompanySpecial();
					companySpecial.setSp_cont(special);
					companySpecial.setCompanyInfo(companyInfo);
					companySpecialRepo.save(companySpecial);
				}
			}
			
		}
		if(spacesArr != null && spacesArr.length > 0) {
			
			for(String space : spacesArr) {
				if(space != null && !space.equals("")) {
					CompanyArea companyArea = new CompanyArea();
					companyArea.setArea(areaRepo.findById(space).get());
					companyArea.setCompanyInfo(companyInfo);
					companyAreaRepo.save(companyArea);	
				}
			}
			
		}
				
		return companyInfo;
	}


	@Override
	public CompanyInfo getCompanyInfo(String companyId) {
		Company company = companyRepo.findById(companyId).get();
		List<CompanyInfo> list = companyInfoRepo.findByCompany(company);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	

}
