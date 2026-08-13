package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractVO;

@Repository
public interface PurchaseContractRepo extends JpaRepository<PurchaseContractVO, Long> {

    @Query(nativeQuery = true, value = "select * from purchase_contract where purchasecontract_id=?1")
    PurchaseContractVO getPurchaseContractById(Long id);

    @Query(nativeQuery = true, value = "select * from purchase_contract where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
    List<PurchaseContractVO> getPurchaseContractByOrgId(Long orgId, Long branchId);

    boolean existsByContractNoAndOrgId(String contractNo, Long orgId);

    // REPLACE with these — ref no only, no supplier in the key
    boolean existsBySupplierRefNoAndOrgId(String supplierRefNo, Long orgId);

    boolean existsBySupplierRefNoAndOrgIdAndIdNot(String supplierRefNo, Long orgId, Long id);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getPurchaseContractDocId(Long orgId, String screenCode);
    
    @Query(value = """
            SELECT
                d.item_id,
                i.item_code,
                i.item_description
            FROM purchase_contract_details d
            INNER JOIN item i
                    ON d.item_id = i.item_id
            WHERE d.purchasecontract_id = :contractId
            ORDER BY i.item_code
            """, nativeQuery = true)
    List<Object[]> getItemsByContractId(
            @Param("contractId") Long contractId);
}