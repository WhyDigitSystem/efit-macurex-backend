package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BulkIssueIndentVO;

@Repository
public interface BulkIssueIndentRepo extends JpaRepository<BulkIssueIndentVO, Long>{

	
	 @Query(value = """
	            SELECT *
	            FROM bulk_issue_indent
	            WHERE org_id = :orgId
	              AND branch = :branch
	              AND cancel = 0
	            ORDER BY bulk_issue_indent_id DESC
	            """, nativeQuery = true)
	    List<BulkIssueIndentVO> findByOrgIdAndBranch(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);

		@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	 String getBulkIssueIndentDocId(Long orgId, String financialYear, String screenCode);
}
