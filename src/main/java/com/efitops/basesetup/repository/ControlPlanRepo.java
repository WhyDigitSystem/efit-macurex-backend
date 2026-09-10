package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanRepo extends JpaRepository<ControlPlanVO, Long> {

  
	@Query(value = """
	        SELECT
	            m.machine_equipments_master_id AS id,
	            m.machine_instrument_no AS mt,
	            m.machine_instrument_name AS description
	        FROM machine_equipments_master m
	        WHERE m.active = TRUE
	          AND (m.cancel = FALSE OR m.cancel IS NULL)
	          AND m.org_id = :orgId
	          AND m.branch = :branch

	        UNION

	        SELECT
	            t.tool_master_basic_id AS id,
	            t.tool_no AS mt,
	            t.tool_description AS description
	        FROM tool_master_basic t
	        WHERE t.active = TRUE
	          AND (t.cancel = FALSE OR t.cancel IS NULL)

	        ORDER BY mt
	        """, nativeQuery = true)
	List<Object[]> getcontrolplandropdownforMachineFixtureDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	List<ControlPlanVO> findByOrgIdAndBranch_Id(Long orgId, Long branch);
	
	
	@Query(value = """
	        SELECT
	            im.item_id AS itemId,
	            im.item_code AS itemCode,
	            im.item_description AS itemDescription,
	            gm.grademaster_id AS gradeMasterId,
	            gm.grade_code AS gradeCode,
	            gm.grade_description AS gradeDescription
	        FROM item im
	        LEFT JOIN grademaster gm
	            ON gm.grademaster_id = im.grade
	        WHERE im.active = 1
	          AND im.cancel = 0
	          AND im.branch = :branch
	          AND im.org_id = :orgId
	        ORDER BY im.item_code
	        """, nativeQuery = true)
	List<Object[]> getFGItemDropdownforControlPlan(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(nativeQuery = true, value = """
	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = ?1
	          AND fin_year = ?2
	          AND screen_code = ?3
	        """)
	String getControlPlanDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

	
	
}