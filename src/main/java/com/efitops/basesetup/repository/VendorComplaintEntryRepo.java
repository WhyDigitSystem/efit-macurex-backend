package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.VendorComplaintEntryVO;

public interface VendorComplaintEntryRepo extends JpaRepository<VendorComplaintEntryVO, Long> {

	@Query(nativeQuery = true, value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""")
	String getVendorComplaintEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT *
			FROM vendor_complaint_entry_basic
			WHERE org_id = ?1
			  AND cancel = FALSE
			  AND active = TRUE
			ORDER BY vendor_complaint_entry_basic_id DESC
			""", nativeQuery = true)
	List<VendorComplaintEntryVO> getVendorComplaintEntryByOrgId(Long orgId);

//	fgitem dropdown
	@Query(value = """
			SELECT
			    i.item_id AS id,
			    i.item_code AS name
			FROM item i
			INNER JOIN listofvaluesdetails lovd
			    ON lovd.listofvaluesdetails_id = i.item_type
			WHERE lovd.value_description = 'FG'
			AND i.branch = ?1
			AND i.org_id = ?2
			  AND i.active = TRUE
			  AND i.cancel = FALSE
			ORDER BY i.item_id DESC
			""", nativeQuery = true)
	List<Object[]> getFgItemDropdownForVendorComplaintEntry(Long branch, Long orgId);

//	item dropdown for the vendor complaint entry
	@Query(value = """
			SELECT
			    i.item_id AS id,
			    i.item_code AS itemCode,
			    i.item_description AS itemDescription,
			    i.bin_qty AS qty
			FROM item i
			INNER JOIN customer_header ch
			    ON ch.customer_id = i.default_supplier
			WHERE i.default_supplier = ?1
			  AND i.branch = ?2
			  AND i.org_id = ?3
			  AND i.active = TRUE
			  AND i.cancel = FALSE
			  AND ch.active = TRUE
			  AND ch.cancel = FALSE
			ORDER BY i.item_id DESC
			""", nativeQuery = true)
	List<Object[]> getItemDropdownForVendorComplaintEntry(Long supplier, Long branch, Long orgId);

}
