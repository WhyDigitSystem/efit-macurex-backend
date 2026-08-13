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
    
    
    @Query("SELECT p FROM PurchaseContractAmendmentVO p " +
    	       "WHERE p.orgId = :orgId " +
    	       "AND p.branch.id = :branch " +
    	       "AND p.cancel = false " +
    	       "ORDER BY p.contractNo")
    	List<PurchaseContractAmendmentVO> findContractNoDropdown(
    	        @Param("orgId") Long orgId,
    	        @Param("branch") Long branch);

}