package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.PMCheckListMasterVO;

public interface PMCheckListMasterRepo extends JpaRepository<PMCheckListMasterVO, Long> {
	@Query(value = """
			SELECT CONCAT(prefix, LPAD(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = :orgId
			  AND fin_year = :financialYear
			  AND screen_code = :screenCode
			""", nativeQuery = true)
	String getPMCheckListMasterDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);

	@Query(value = """
			SELECT *
			FROM pm_check_list_master_basic
			WHERE cancel = 0
			AND active = 1
			AND org_id = :orgId
			  AND branch = :branch
			  AND cancel = false
			ORDER BY pm_check_list_master_basic_id DESC
			""", nativeQuery = true)
	List<PMCheckListMasterVO> getPMCheckListMasterByOrgId(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
		    SELECT
		        tool_category_detail_id,
		        category
		    FROM tool_category_basic tb
		    INNER JOIN tool_category_detail td
		        ON tb.tool_category_basic_id = td.tool_category_basic_id
		    WHERE tb.org_id = :orgId
		      AND tb.apllicable_for = :applicableFor
		    """, nativeQuery = true)
		List<Object[]> getToolMachineCategoryForPMCheckListMaster(
		        @Param("orgId") Long orgId,
		        @Param("applicableFor") String pmCheckListFor);

	@Query(value = """
			SELECT
			    amb.activity_master_basic_id AS id,
			    amb.activity AS name
			FROM activity_master_basic amb
			WHERE amb.department = :department
			  AND amb.org_id = :orgId
			  AND amb.active = TRUE
			  AND amb.cancel = FALSE
			ORDER BY amb.activity_master_basic_id DESC
			""", nativeQuery = true)
	List<Object[]> getActivityForPMCheckListMaster(@Param("department") Long department, @Param("orgId") Long orgId);
}
