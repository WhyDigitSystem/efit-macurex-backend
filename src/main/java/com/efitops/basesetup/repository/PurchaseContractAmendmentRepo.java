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
              AND cancel = false
            """, nativeQuery = true)
    Integer findMaxRevisionNo(
            @Param("contractNo") String contractNo);
    
    
    @Query(value = """
            SELECT
                purchasecontract_id,
                contract_no
            FROM purchase_contract
            WHERE org_id = :orgId
              AND branch = :branch
              AND active = true
              AND cancel = false
            ORDER BY contract_no
            """, nativeQuery = true)
    List<Object[]> getPurchaseContractDropdown(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

}