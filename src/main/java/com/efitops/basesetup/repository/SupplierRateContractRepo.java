package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractVO;

@Repository
public interface SupplierRateContractRepo extends JpaRepository<SupplierRateContractVO, Long> {

	@Query(value = """
			SELECT *
			FROM supplier_rate_contract
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0 and active=1
			ORDER BY supplier_rate_contract_id DESC
			""", nativeQuery = true)
	List<SupplierRateContractVO> findByOrgIdAndBranch(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSupplierRateContractDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);

	@Query(value = """
	        SELECT 
	            src.doc_id,
	            src.doc_date,
	            src.contract_for,
	            sam.serviceaccmaster_id,
	            sam.service_name,
	            hsn.hsn_id,
	            hsn.hsn,
	            hsn.description,
	            src.tax_percentage,
	            srcd.igst_rate,
	            srcd.cgst_rate,
	            srcd.sgst_rate
	        FROM supplier_rate_contract src

	        LEFT JOIN serviceaccmaster sam
	            ON sam.serviceaccmaster_id = src.service_name

	        LEFT JOIN hsn hsn
	            ON hsn.hsn_id = src.hsn_sac_code

	        LEFT JOIN supplier_rate_contract_item_details srcd
	            ON srcd.supplier_rate_contract_id = src.supplier_rate_contract_id

	        WHERE src.customer = :customer
	          AND src.org_id = :orgId
	          AND src.branch = :branch
	          AND src.active = 1
	          AND src.cancel = 0
	        """, nativeQuery = true)
	Set<Object[]> getSupplierRateContractDropdown(
	        @Param("customer") Long customer,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	

	@Query(value = """
			SELECT
			    srcid.supplier_rate_contract_item_details_id AS id,
			    srcid.incoming_item_code AS incomingItem,
			    i.item_code AS itemCode,
			    i.item_description AS itemDescription,
			    srcid.purchase_unit AS unit,
			    u.unit_id AS unitId,
			    u.description AS description,
			    srcid.rate AS rate
			FROM supplier_rate_contract src
			JOIN supplier_rate_contract_item_details srcid
			    ON srcid.supplier_rate_contract_id = src.supplier_rate_contract_id
			LEFT JOIN item i
			    ON i.item_id = srcid.incoming_item_code
			LEFT JOIN unitmaster u
			    ON u.unitmaster_id = srcid.purchase_unit
			WHERE src.doc_id = :docId
			  AND src.org_id = :orgId
			  AND src.branch = :branch
			  AND src.active = 1
			  AND src.cancel = 0
			""", nativeQuery = true)
	Set<Object[]> getSupplierRateContractItemDetails(
			@Param("docId") String docId,
			@Param("orgId") Long orgId,
			@Param("branch") Long branch);	
}
