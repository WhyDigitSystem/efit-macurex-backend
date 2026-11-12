package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SupplierRateContractAmendmentVO;

@Repository
public interface SupplierRateContractAmendmentRepo extends JpaRepository<SupplierRateContractAmendmentVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSupplierRateContractAmendmentDocId(Long orgId, String financialYear, String screenCode);

	
	@Query(value = """
	        SELECT *
	        FROM supplier_rate_contract_amendment
	        WHERE org_id = :orgId
	        AND branch = :branch and cancel=0 
	        """, nativeQuery = true)
	List<SupplierRateContractAmendmentVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);


	@Query(value = """
	        SELECT
	            new_valid_from,
	            new_valid_to,
	            CAST(revision_no AS UNSIGNED) + 1
	        FROM supplier_rate_contract_amendment
	        WHERE contract_no = :contractNo
	        AND org_id = :orgId
	        AND branch = :branch
	        ORDER BY supplier_rate_contract_amendment_id DESC
	        LIMIT 1
	        """, nativeQuery = true)
	List<Object[]> getRevisionNoDetailsForSupplierRateContractAmd(
	        @Param("contractNo") String contractNo,
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

	            CASE
	                WHEN srcaid.supplier_rate_contract_amendment_item_details_id IS NOT NULL
	                    THEN srcaid.new_rate
	                ELSE 0
	            END AS oldRate

	        FROM supplier_rate_contract src

	        JOIN supplier_rate_contract_item_details srcid
	            ON srcid.supplier_rate_contract_id =
	               src.supplier_rate_contract_id

	        LEFT JOIN item i
	            ON i.item_id = srcid.incoming_item_code

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = srcid.purchase_unit

	        LEFT JOIN supplier_rate_contract_amendment srca
	            ON srca.contract_no = src.doc_id
	            AND srca.org_id = src.org_id
	            AND srca.branch = src.branch
	            AND srca.active = 1
	            AND srca.cancel = 0

	        LEFT JOIN supplier_rate_contract_amendment_item_details srcaid
	            ON srcaid.supplier_rate_contract_amendment_id =
	               srca.supplier_rate_contract_amendment_id
	            AND srcaid.item = srcid.incoming_item_code

	        WHERE src.doc_id = :docId
	        AND src.org_id = :orgId
	        AND src.branch = :branch
	        AND src.active = 1
	        AND src.cancel = 0
	        """, nativeQuery = true)
	List<Object[]> getSupplierRateContractItemDetailsForSRCAmd(
	        @Param("docId") String docId,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

}
