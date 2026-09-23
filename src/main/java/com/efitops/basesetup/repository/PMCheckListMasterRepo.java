package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.PMCheckListMasterVO;

public interface PMCheckListMasterRepo extends JpaRepository<PMCheckListMasterVO, Long>{
	  @Query(value = """
	            SELECT CONCAT(prefix, LPAD(last_no, 5, 0)) AS docid
	            FROM documenttypemapping_details
	            WHERE org_id = :orgId
	              AND fin_year = :financialYear
	              AND screen_code = :screenCode
	            """, nativeQuery = true)
	    String getPMCheckListMasterDocId(
	            @Param("orgId") Long orgId,
	            @Param("financialYear") String financialYear,
	            @Param("screenCode") String screenCode);
	

}
