package com.homefix.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.homefix.domain.Member;

public interface DashboardRepository extends CrudRepository<Member, String>{
	
	@Query(value = "WITH RECURSIVE cte AS\r\n"
			+ "(\r\n"
			+ "	SELECT DATE_ADD(NOW(), INTERVAL -11 MONTH) AS d\r\n"
			+ "	\r\n"
			+ "	UNION all\r\n"
			+ "	\r\n"
			+ "	SELECT DATE_ADD(d, INTERVAL 1 MONTH)  AS d\r\n"
			+ "	FROM cte\r\n"
			+ "	WHERE d < DATE_ADD(NOW(), INTERVAL -1 MONTH)\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "	DATE_FORMAT(c.d, '%Y-%m') AS MONTH,\r\n"
			+ "	IFNULL(m.users,0) users\r\n"
			+ "FROM cte c\r\n"
			+ "	LEFT OUTER JOIN (\r\n"
			+ "		SELECT DATE_FORMAT(SUBDATE, '%Y-%m') month, COUNT(*) users\r\n"
			+ "		FROM member\r\n"
			+ "		GROUP BY MONTH\r\n"
			+ "		) m\r\n"
			+ "	ON date_format(c.d, '%Y-%m') = m.month", nativeQuery = true)
	public List<Object[]> searchAggNewUser();
	
	@Query(value= "WITH RECURSIVE cte AS\r\n"
			+ "(\r\n"
			+ "	SELECT DATE_ADD(NOW(), INTERVAL -11 MONTH) AS d\r\n"
			+ "	\r\n"
			+ "	UNION all\r\n"
			+ "	\r\n"
			+ "	SELECT DATE_ADD(d, INTERVAL 1 MONTH)  AS d\r\n"
			+ "	FROM cte\r\n"
			+ "	WHERE d < DATE_ADD(NOW(), INTERVAL -1 MONTH)\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "	DATE_FORMAT(c.d, '%Y-%m') AS MONTH,\r\n"
			+ "	IFNULL(p.payments,0) payments\r\n"
			+ "FROM cte c\r\n"
			+ "	LEFT OUTER JOIN (\r\n"
			+ "		SELECT DATE_FORMAT(pm_day, '%Y-%m') month, SUM(amount) payments\r\n"
			+ "		FROM payment\r\n"
			+ "		GROUP BY MONTH\r\n"
			+ "		) p\r\n"
			+ "	ON date_format(c.d, '%Y-%m') = p.month", nativeQuery = true)
	public List<Object[]> searchAggPayments();
}
