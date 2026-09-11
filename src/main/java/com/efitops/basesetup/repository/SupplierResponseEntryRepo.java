package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	            c.customer_id AS supplierId,
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

	@Query(value = """
		    SELECT
		        i.item_id,
		        i.item_code,
		        i.item_description
		    FROM item i
		    INNER JOIN customer_header ch
		        ON ch.customer_id = i.default_supplier
		    INNER JOIN listofvaluesdetails lov
		        ON lov.listofvaluesdetails_id = ch.customer_category
		    WHERE ch.customer_id = :supplierId
		      AND ch.org_id = :orgId
		      AND ch.branch = :branch
		      AND i.org_id = :orgId
		      AND i.branch = :branch
		      AND lov.value_description = 'Supplier'
		      AND i.active = 1
		      AND ch.active = 1
		    """, nativeQuery = true)
		List<Object[]> getItemDropdownForSupplierResponseEntry(
		        @Param("supplierId") Long supplierId,
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);
	
}
