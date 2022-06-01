package com.homefix.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Company;
import com.homefix.domain.CompanyDto;


public interface CompanyRepository extends CrudRepository<Company, String>{

	public Long countById(String id);
	
	public List<Company> findByEmail(String email) ;
	
	public Long countByNum(String num);
	
	public List<Company> findByIdAndPass(String Id, String pass);

	public void save(CompanyDto dto);
	
	@Query(value = 
				"SELECT "
					+ "	co.cid id, "
					+ "	co.co_name name, "
					+ "	ifnull(ch.chatting, 0) chatting, "
					+ "	ifnull(con.contract, 0) contract, "
					+ "	ifnull(re.report, 0) report, "
					+ "	datediff(co.deadday, NOW()) pm_day "
				+ " FROM "
					+ "	company co "
					+ "	LEFT OUTER JOIN (SELECT cid, COUNT(*) chatting FROM chatting GROUP BY cid) ch "
					+ "	ON co.cid = ch.cid "
					+ "	LEFT OUTER JOIN (SELECT cid, COUNT(*) contract FROM contract GROUP BY cid) con "
					+ "	ON co.cid = con.cid "
					+ "	LEFT OUTER JOIN (SELECT cid, COUNT(*) report FROM cp_report GROUP BY cid) re "
					+ "	ON co.cid = re.cid", nativeQuery = true)
	public List<Map<String, Object>> findAllForAdmin();
	
}
