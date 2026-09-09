package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.SupplierResponseEntryVO;

public interface SupplierResponseEntryRepo extends JpaRepository<SupplierResponseEntryVO, Long>{
	
	@Query(nativeQuery = true, value = """
	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = ?1
	          AND fin_year = ?2
	          AND screen_code = ?3
	        """)
	String getSupplierResponseEntryDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);
	
	@Query(value = """
	        SELECT *
	        FROM supplier_response_entry_basic
	        WHERE org_id = ?1
	          AND cancel = FALSE
	          AND active = TRUE
	        ORDER BY supplier_response_entry_basic_id DESC
	        """, nativeQuery = true)
	List<SupplierResponseEntryVO> getSupplierResponseEntryByOrgId(
	        Long orgId);
	
	@Query(value = """
	        SELECT
	            vc.doc_id AS docId,
	            vc.doc_date AS docDate,
	            i.item_code AS productNo,
	            i.item_description AS productName,
	            c.customer_code AS supplierNo,
	            c.customer_name AS supplierName
	        FROM vendor_complaint_entry_basic vc
	        INNER JOIN item i
	            ON i.item_id = vc.fg_item
	        INNER JOIN customer_header c
	            ON c.customer_id = vc.supplier
	        WHERE vc.org_id = ?1
	          AND vc.active = TRUE
	          AND vc.cancel = FALSE
	          AND i.active = TRUE
	          AND i.cancel = FALSE
	          AND c.active = TRUE
	          AND c.cancel = FALSE
	        ORDER BY vc.vendor_complaint_entry_basic_id DESC
	        """, nativeQuery = true)
	List<Object[]> getComplaintNoDropdownForSupplierResponseEntry(
	        Long orgId);

}
