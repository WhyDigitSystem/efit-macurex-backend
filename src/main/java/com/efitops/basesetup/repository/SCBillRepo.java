package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.SCBillVO;

public interface SCBillRepo extends JpaRepository<SCBillVO, Long> {

	List<SCBillVO> findByOrgIdAndCancelFalse(Long orgId);
	
	
	@Query(nativeQuery = true, value = """
	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = ?1
	          AND fin_year = ?2
	          AND screen_code = ?3
	        """)
	String getSCBillDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

}
