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
            SELECT COALESCE(MAX(revision_no),0)
            FROM pcamdbasic
            WHERE contract_no = :contractNo
              AND org_id = :orgId
              AND branch = :branch
              AND cancel = false
            """, nativeQuery = true)
    Integer getPurchaseContractAmdRevisionNo(
            @Param("contractNo") String contractNo,
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);
    
    
    @Query(value = """
            SELECT
                p.purchase_contract_basic_id,
                p.doc_id
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
                   d.unit_id AS unitId
            FROM item i
            INNER JOIN purchase_contract_details d
                    ON i.item_id = d.item_id
            INNER JOIN purchase_contract_basic b
                    ON b.purchase_contract_basic_id = d.purchase_contract_basic_id
            WHERE b.cancel = 0
              AND b.doc_id = :docId
              AND b.org_id = :orgId
              AND b.branch = :branch
            """, nativeQuery = true)
    List<Object[]> getPurchaseContractAmendmentItemCodeDropdown(
            @Param("docId") String docId,
            @Param("branch") Long branch,
            @Param("orgId") Long orgId);
}