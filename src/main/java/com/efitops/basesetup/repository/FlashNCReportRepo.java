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
	    
	    @Query(value = """
	            SELECT
	                lovd.listofvaluesdetails_id AS id,
	                lovd.value_code AS valueCode,
	                lovd.value_description AS valueDescription
	            FROM listofvaluesdetails lovd
	            WHERE lovd.listofvalues_id = :listOfValuesId
	              AND lovd.active = 1
	            ORDER BY lovd.listofvaluesdetails_id DESC
	            """, nativeQuery = true)
	    List<Object[]> getFromDeptDropdownForFlashNCReport(
	            @Param("listOfValuesId") Long listOfValuesId);
	    
	    @Query(value = """
	            SELECT
	                lovd.listofvaluesdetails_id AS id,
	                lovd.value_code AS valueCode,
	                lovd.value_description AS valueDescription
	            FROM listofvaluesdetails lovd
	            WHERE lovd.listofvalues_id = :listOfValuesId
	              AND lovd.listofvaluesdetails_id <> :fromDept
	              AND lovd.active = 1
	            ORDER BY lovd.listofvaluesdetails_id DESC
	            """, nativeQuery = true)
	    List<Object[]> getToDepartmentDropdownForFlashNCReport(
	            @Param("listOfValuesId") Long listOfValuesId,
	            @Param("fromDept") Long fromDept);


	    @Query(value = """
	            SELECT
	                iib.mrin_grn_no AS mrinGrnNo,
	                ch.customer_code AS supplierCode,
	                ch.customer_name AS supplierName,
	                iib.sup_inv_no AS invoiceNo,
	                i.item_code AS item,
	                i.item_description AS itemDescription,
	                iib.mrin_grn_date AS mrinGrnDate,
	                iib.po_pc_jo_no AS poNo,
	                iid.received_qty AS qty,
	                'MRIN' AS sourceType
	            FROM inward_inspection_basic iib

	            INNER JOIN inward_inspection_details iid
	                ON iid.inward_inspection_basic_id =
	                   iib.inward_inspection_basic_id

	            LEFT JOIN customer_header ch
	                ON ch.customer_id = iib.supplier_code

	            LEFT JOIN item i
	                ON i.item_id = iid.item

	            WHERE iib.org_id = :orgId
	              AND iib.branch = :branch
	              AND iib.active = 1
	              AND iib.cancel = 0


	            UNION ALL


	            SELECT
	                gb.doc_id AS mrinGrnNo,
	                ch.customer_code AS supplierCode,
	                ch.customer_name AS supplierName,
	                gb.invoice_no AS invoiceNo,
	                i.item_code AS item,
	                i.item_description AS itemDescription,
	                gb.doc_date AS mrinGrnDate,
	                gb.po_no AS poNo,
	                gd.received_qty AS qty,
	                'GRN' AS sourceType
	            FROM grn_basic gb

	            INNER JOIN grn_details gd
	                ON gd.grn_basic_id = gb.grn_basic_id

	            LEFT JOIN customer_header ch
	                ON ch.customer_id = gb.supplier_code

	            LEFT JOIN item i
	                ON i.item_id = gd.item

	            WHERE gb.org_id = :orgId
	              AND gb.branch = :branch
	              AND gb.active = 1
	              AND gb.cancel = 0

	            ORDER BY mrinGrnNo DESC
	            """, nativeQuery = true)
	    List<Object[]> getMRINGRNDropdownForFlashNCReport(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
}
