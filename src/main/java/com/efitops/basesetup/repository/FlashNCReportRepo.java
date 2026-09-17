package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.FlashNCReportVO;

public interface FlashNCReportRepo extends JpaRepository<FlashNCReportVO, Long>{

	  @Query(value = """
	            SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	            FROM documenttypemapping_details
	            WHERE org_id = ?1
	              AND fin_year = ?2
	              AND screen_code = ?3
	            """, nativeQuery = true)
	    String getFlashNCReportDocId(
	            Long orgId,
	            String financialYear,
	            String screenCode);


	    @Query(value = """
	            SELECT *
	            FROM flash_nc_report_basic
	            WHERE active = 1
	            AND cancel = 0
	            AND org_id = :orgId
	            AND branch = :branch
	            """, nativeQuery = true)
	    List<FlashNCReportVO> getFlashNCReportByOrgId(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
	    @Query(value = """
	            SELECT
	                em.employeemaster_id AS employeeId,
	                em.employee_id AS employeeCode,
	                em.emp_name AS employeeName
	            FROM employeemaster em
	            INNER JOIN department d
	                ON d.departmentid = em.department
	            WHERE UPPER(d.department_name) = 'QUALITY'
	              AND em.org_id = :orgId
	              AND em.branch = :branch
	              AND em.active = 1
	              AND em.cancel = 0
	              AND d.active = 1
	              AND d.cancel = 0
	            ORDER BY em.employeemaster_id DESC
	            """, nativeQuery = true)
	    List<Object[]> getQualityEmployeesForFlashNCReport(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
}
