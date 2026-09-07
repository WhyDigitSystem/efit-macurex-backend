package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderAmendmentVO;

@Repository 
public interface JobOrderAmendmentRepo extends JpaRepository<JobOrderAmendmentVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getJobOrderAmendmentDocId(Long orgId, String financialYear, String screenCode);


	@Query(value = """
	        SELECT COALESCE(MAX(CAST(revision_no AS UNSIGNED)), 0) + 1
	        FROM job_order_amendment
	        WHERE job_order_no = :jobOrderNo
	          AND branch = :branch
	          AND org_id = :orgId
	          AND active = 1
	          AND cancel = 0
	        """, nativeQuery = true)
	Integer getNextRevisionNoForJobOrderAmd(
	        @Param("jobOrderNo") String jobOrderNo,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);


	@Query("""
	        SELECT j
	        FROM JobOrderAmendmentVO j
	        WHERE j.orgId = :orgId
	          AND j.branch.id = :branch
	        ORDER BY j.id DESC
	        """)
	List<JobOrderAmendmentVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
