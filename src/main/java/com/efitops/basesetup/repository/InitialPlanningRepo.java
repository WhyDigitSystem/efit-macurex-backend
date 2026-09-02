package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.InitialPlanningVO;

public interface InitialPlanningRepo extends JpaRepository<InitialPlanningVO, Long> {

//	List<InitialPlanningVO> getInitialPlanningByOrgId(Long orgId);

	@Query(value = """
			SELECT *
			FROM initial_planning_basic
			WHERE org_id = ?1
			  AND cancel = FALSE
			ORDER BY initial_planning_basic_id DESC
			""", nativeQuery = true)
	List<InitialPlanningVO> getInitialPlanningByOrgId(Long orgId);

	@Query(nativeQuery = true, value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""")
	String getInitialPlanningDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT
			    i.item_id,
			    i.item_code,
			    i.item_description,

			    lov.listofvaluesdetails_id,
			    lov.value_code,
			    lov.value_description,

			    gm.grademaster_id,
			    gm.grade_code,
			    gm.grade_description

			FROM item i

			INNER JOIN listofvaluesdetails lov
			    ON i.item_type = lov.listofvaluesdetails_id

			LEFT JOIN grademaster gm
			    ON i.grade = gm.grademaster_id

			WHERE i.item_type = ?1
			  AND i.org_id = ?2
			  AND i.active = TRUE
			  AND i.cancel = FALSE
			  AND lov.active = TRUE
			  AND (gm.active = TRUE OR gm.grademaster_id IS NULL)
			  AND (gm.cancel = FALSE OR gm.grademaster_id IS NULL)

			ORDER BY i.item_code
			""", nativeQuery = true)
	List<Object[]> getItemDropdownForInitialPlanning(Long itemType, Long orgId);

//	dropdown for parameter 

	@Query(value = """
			SELECT
			    p.parameter_master_basic_id,
			    p.parameter_code,
			    p.parameter_description,
			    p.parameter_type,
			    lov.value_code,
			    lov.value_description
			FROM parameter_master_basic p
			INNER JOIN listofvaluesdetails lov
			    ON p.parameter_type = lov.listofvaluesdetails_id
			WHERE p.org_id = ?1
			  AND p.active = TRUE
			  AND p.cancel = FALSE
			  AND lov.active = TRUE
			ORDER BY p.parameter_description
			""", nativeQuery = true)
	List<Object[]> getParameterDropdownForInitialPlanning(Long orgId);
	
	
	@Query(value = """
	        SELECT
	            mem.machine_equipments_master_id AS id,
	            mem.machine_instrument_no AS machineInstrumentNo,
	            mem.machine_instrument_name AS machineInstrumentName
	        FROM machine_equipments_master mem
	        LEFT JOIN listofvaluesdetails lov
	            ON lov.listofvaluesdetails_id = mem.type
	        WHERE mem.active = 1
	          AND (mem.cancel = 0 OR mem.cancel IS NULL)
	          AND mem.org_id = :orgId
	          AND mem.branch = :branch
	        """, nativeQuery = true)
	List<Object[]> getMachineInstrumentDropdownForInitialPlanning(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

}
