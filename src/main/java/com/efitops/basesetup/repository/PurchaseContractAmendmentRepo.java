package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;

public interface PurchaseContractAmendmentRepo
        extends JpaRepository<PurchaseContractAmendmentVO, Long> {

    @Query(value = """
            SELECT *
            FROM pcamdbasic
            WHERE org_id = :orgId
              AND branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY pcamdbasic_id DESC
            """, nativeQuery = true)
    List<PurchaseContractAmendmentVO> findByOrgId(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

    @Query(value = """
            SELECT COALESCE(MAX(CAST(revision_no AS UNSIGNED)), 0) + 1
            FROM pcamdbasic
            WHERE contract_no = ?1
              AND org_id = ?2
              AND branch = ?3
              AND cancel = FALSE
            """, nativeQuery = true)
    Integer getPurchaseContractAmdRevisionNo(
            String contractNo,
            Long orgId,
            Long branch);
    
    @Query(value = """
            SELECT
                p.purchase_contract_basic_id,
                p.doc_id,p.doc_date
            FROM purchase_contract_basic p
            WHERE p.org_id = :orgId
              AND p.branch = :branch
              AND p.cancel = 0
              AND p.supplier = :customerId
            ORDER BY p.doc_id
            """, nativeQuery = true)
    List<Object[]> findContractNoDropdownforPurchaseContractAmendment(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch,
            @Param("customerId") Long customerId);
    
    
    @Query(value = """
          SELECT DISTINCT
                   i.item_id AS id,
                   i.item_code AS itemCode,
                   i.item_description AS itemDescription,
                   u.unit_id AS unitId,
                   d.unit
            FROM item i
            INNER JOIN purchase_contract_details d
                    ON i.item_id = d.item
            INNER JOIN purchase_contract_basic b
                    ON b.purchase_contract_basic_id = d.purchase_contract_basic_id 
                    left join unitmaster u on u.unitmaster_id=d.unit
            WHERE b.cancel = 0
               AND b.doc_id = :docId
               AND b.org_id = :orgId
             and  b.branch = :branch
            """, nativeQuery = true)
    List<Object[]> getPurchaseContractAmendmentItemCodeDropdown(
            @Param("docId") String docId,
            @Param("branch") Long branch,
            @Param("orgId") Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseContractAmendmentDocId(Long orgId, String financialYear, String screenCode);
}