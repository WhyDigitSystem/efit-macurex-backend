package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;

public interface ProcessSheetCompRoutingRepo extends JpaRepository<ProcessSheetCompRoutingVO, Long> {

	

	@Query("SELECT p FROM ProcessSheetCompRoutingVO p " +
		       "WHERE p.orgId = :orgId " +
		       "AND p.branch.id = :branch")
		List<ProcessSheetCompRoutingVO> findByOrgIdAndBranch(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);

	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getProcessSheetCompRoutingDocId(Long orgId, String financialYear, String screenCode);
	
}
