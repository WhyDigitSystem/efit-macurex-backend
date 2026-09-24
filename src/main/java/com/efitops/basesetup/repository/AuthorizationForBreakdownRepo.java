package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.AuthorizationForBreakdownVO;

public interface AuthorizationForBreakdownRepo extends JpaRepository< AuthorizationForBreakdownVO, Long>{

	@Query(value = """

	        SELECT CONCAT(

	            dtmd.prefix,

	            LPAD(dtmd.last_no + 1, 5, '0')

	        )

	        FROM documenttypemapping_details dtmd

	        WHERE dtmd.org_id = :orgId

	          AND dtmd.fin_year = :financialYear

	          AND dtmd.screen_code = :screenCode

	        """, nativeQuery = true)
	String getAuthorizationForBreakdownDocId(
	        @Param("orgId") Long orgId,
	        @Param("financialYear") String financialYear,
	        @Param("screenCode") String screenCode);
	
	@Query(value = """

	        SELECT *
	        FROM authorization_for_breakdown_basic
	        WHERE cancel = false
	        AND active = true
	        AND org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	        ORDER BY authorization_for_breakdown_basic_id DESC

	        """, nativeQuery = true)
	List<AuthorizationForBreakdownVO> findByOrgIdAndBranchId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT
	            doc_id AS docId,
	            doc_date AS docDate,
	            breakdown_no AS breakdownNo,
	            breakdown_date AS breakdownDate,
	            nature_of_problem AS natureOfProblem,
	            action_taken AS actionTaken,
	            rectification_time AS rectificationTime,
	            machine_tool_no AS machineToolNo
	        FROM machine_tool_rectification_basic
	        WHERE branch = :branch
	          AND org_id = :orgId
	          AND active = true
	          AND cancel = false
	        ORDER BY machine_tool_rectification_basic_id DESC
	        """, nativeQuery = true)
	List<Object[]> getMachineToolRectificationDetails(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
}
