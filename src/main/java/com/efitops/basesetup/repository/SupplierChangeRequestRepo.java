package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SupplierChangeRequestVO;

public interface SupplierChangeRequestRepo extends JpaRepository<SupplierChangeRequestVO, Long>{
	
	@Query(value = """
	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = :orgId
	          AND fin_year = :financialYear
	          AND screen_code = :screenCode
	        """, nativeQuery = true)
	String getSupplierChangeRequestDocId(
	        @Param("orgId") Long orgId,
	        @Param("financialYear") String financialYear,
	        @Param("screenCode") String screenCode);
	
	@Query(value = """
	        SELECT *
	        FROM supplier_change_request_basic
	        WHERE cancel = 0
	        AND active = 1
	        AND org_id = :orgId
	        AND branch = :branch
	        ORDER BY supplier_change_request_basic_id DESC
	        """, nativeQuery = true)
	List<SupplierChangeRequestVO> getSupplierChangeRequestByOrgId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT DISTINCT
	            ch.customer_id AS id,
	            ch.customer_code AS vendorCode,
	            ch.customer_name AS supplierName
	        FROM customer_header ch

	        LEFT JOIN listofvaluesdetails lovd1
	            ON lovd1.listofvaluesdetails_id = ch.customer_category

	        LEFT JOIN listofvaluesdetails lovd2
	            ON lovd2.listofvaluesdetails_id = ch.customer_category1

	        LEFT JOIN listofvaluesdetails lovd3
	            ON lovd3.listofvaluesdetails_id = ch.customer_category2

	        WHERE ch.org_id = :orgId
	          AND ch.branch = :branch

	          AND (
	                UPPER(lovd1.value_description) = 'VENDOR'
	                OR UPPER(lovd2.value_description) = 'VENDOR'
	                OR UPPER(lovd3.value_description) = 'VENDOR'
	              )

	          AND ch.active = 1
	          AND ch.cancel = 0

	        ORDER BY ch.customer_id DESC
	        """, nativeQuery = true)
	List<Object[]> getVendorCodeDropdownForSupplierChangeRequest(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
//	purchase employees/buyer  
	
	@Query(value = """
	        SELECT
	            em.employeemaster_id AS employeeId,
	            em.employee_id AS employeeCode,
	            em.emp_name AS employeeName
	        FROM employeemaster em
	        INNER JOIN department d
	            ON d.departmentid = em.department
	        WHERE em.org_id = :orgId
	          AND em.branch = :branch
	          AND UPPER(d.department_name) = 'PURCHASE'
	          AND em.active = 1
	          AND em.cancel = 0
	          AND d.active = 1
	          AND d.cancel = 0
	        ORDER BY em.employeemaster_id DESC
	        """, nativeQuery = true)
	List<Object[]> getPurchaseEmployeesDropdownForSupplierChangeRequest(
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
	        WHERE em.org_id = :orgId
	          AND em.branch = :branch
	          AND UPPER(d.department_name) = 'TDC'
	          AND em.active = 1
	          AND em.cancel = 0
	          AND d.active = 1
	          AND d.cancel = 0
	        ORDER BY em.employeemaster_id DESC
	        """, nativeQuery = true)
	List<Object[]> getTDCEmployeesDropdownForSupplierChangeRequest(
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
	        WHERE em.org_id = :orgId
	          AND em.branch = :branch
	          AND UPPER(d.department_name) = 'PRODUCTION'
	          AND em.active = 1
	          AND em.cancel = 0
	          AND d.active = 1
	          AND d.cancel = 0
	        ORDER BY em.employeemaster_id DESC
	        """, nativeQuery = true)
	List<Object[]> getProductionEmployeesDropdownForSupplierChangeRequest(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
