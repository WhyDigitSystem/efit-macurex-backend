package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.EngineeringChangeRecordVO;

public interface EngineeringChangeRecordRepo extends JpaRepository<EngineeringChangeRecordVO, Long>{
	@Query(value = """

	        SELECT *

	        FROM engineering_change_record_basic

	        WHERE org_id = ?1
	         
	         AND branch = ?2

	          AND cancel = FALSE

	          AND active = TRUE

	        ORDER BY engineering_change_record_basic_id DESC

	        """, nativeQuery = true)

	List<EngineeringChangeRecordVO> getEngineeringChangeRecordByOrgId(Long orgId,Long branch);
	
	
	
	@Query(nativeQuery = true, value = """

	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid

	        FROM documenttypemapping_details

	        WHERE org_id = ?1

	          AND fin_year = ?2

	          AND screen_code = ?3

	        """)
	String getEngineeringChangeRecordDocId(Long orgId, String financialYear, String screenCode);

}
