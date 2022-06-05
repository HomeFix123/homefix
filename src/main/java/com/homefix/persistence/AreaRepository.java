package com.homefix.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Area;

public interface AreaRepository extends CrudRepository<Area, String>{
	List<Area> findByAnameOrderById(String aname);
	
	@Query(value = "SELECT a.aname FROM area a WHERE a.dt_area IS NULL ORDER BY a.id")
	List<String> findAname();
}
