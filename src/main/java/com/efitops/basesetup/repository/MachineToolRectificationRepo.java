package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.MachineToolRectificationVO;

public interface MachineToolRectificationRepo extends JpaRepository<MachineToolRectificationVO, Long> {

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
	String getMachineToolRectificationDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);

	@Query(value = """
			SELECT *
			FROM machine_tool_rectification_basic
			WHERE cancel = false
			AND active = true
			AND org_id = :orgId
			  AND branch = :branch
			  AND cancel = false
			ORDER BY machine_tool_rectification_basic_id DESC
			""", nativeQuery = true)
	List<MachineToolRectificationVO> getMachineToolRectificationByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(value = """
			SELECT
			    mtb.machine_tool_breakdown_basic_id AS id,
			    mtb.doc_id AS breakdownNo,
			    mtb.reported_date AS breakdownDate,
			    mtb.reported_time AS time,
			    mtb.machine_tool_id_inst AS machineToolNo,
			    mtb.remarks AS description,
			    l.value_code AS maintenanceType,
			    mtb.nature_of_problem AS natureOfProblem,
			    mtb.estimated_time AS timeTakenForRectification,
			    mtb.location AS location
			FROM machine_tool_breakdown_basic mtb
			INNER JOIN listofvaluesdetails l
			    ON l.listofvaluesdetails_id = mtb.maintenance_type
			WHERE mtb.org_id = :orgId
			  AND mtb.branch = :branch
			  AND mtb.active = TRUE
			  AND mtb.cancel = FALSE
			ORDER BY mtb.machine_tool_breakdown_basic_id DESC
			""", nativeQuery = true)
	List<Object[]> getBreakdownDetailsForRectification(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
			SELECT
			    em.employeemaster_id AS id,
			    em.employee_id AS employeeId,
			    em.emp_name AS name
			FROM employeemaster em
			INNER JOIN branch b
			    ON b.branch_id = em.branch
			INNER JOIN department d
			    ON d.departmentid = em.department
			WHERE em.org_id = :orgId
			  AND em.branch = :branch
			  AND em.department = :department
			  AND em.active = TRUE
			  AND em.cancel = FALSE
			  AND b.active = TRUE
			  AND b.cancel = FALSE
			  AND d.active = TRUE
			  AND d.cancel = FALSE
			ORDER BY em.employeemaster_id DESC
			""", nativeQuery = true)
	List<Object[]> getPrepareByForMachineToolRectification(@Param("orgId") Long orgId, @Param("branch") Long branch,
			@Param("department") Long department);

}
