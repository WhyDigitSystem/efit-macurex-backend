package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.InitialStageInspectionVO;

public interface InitialStageInspectionRepo extends JpaRepository<InitialStageInspectionVO, Long> {
	
	
	   List<InitialStageInspectionVO> findByOrgIdAndBranch_Id(
	            Long orgId,
	            Long branch);
	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
	        + "from documenttypemapping_details "
	        + "where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getInitialStageInspectionDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);
	
	
	
	
	@Query(nativeQuery = true, value = "SELECT "
	        + "JOB.job_order_basic_id AS id, "
	        + "JOB.doc_id AS name "
	        + "FROM job_order_basic JOB "
	        + "WHERE JOB.org_id = ?1 "
	        + "AND JOB.branch = ?2 "
	        + "AND JOB.vendor = ?3 "
	        + "AND JOB.active = TRUE "
	        + "AND JOB.cancel = FALSE "
	        + "AND JOB.doc_id IS NOT NULL "
	        + "ORDER BY JOB.job_order_basic_id DESC")
	List<Object[]> getWorkOrderNoDropDownForInitialStageInspection(
	        Long orgId,
	        Long branch,
	        Long partyId);

}
