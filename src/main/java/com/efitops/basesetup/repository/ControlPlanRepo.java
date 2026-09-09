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
	            cp.control_plan_basic_id AS id,
	            cp.plan_no AS docNo
	        FROM control_plan_basic cp
	          AND cp.branch = :branch
	          AND cp.org_id = :orgId
	          AND cp.active = TRUE
	          AND cp.cancel = FALSE
	        ORDER BY cp.plan_no
	        """, nativeQuery = true)
	List<Object[]> getControlPlanDropdownitemcode(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);

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