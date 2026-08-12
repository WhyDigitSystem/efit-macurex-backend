// PurchaseBillRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface PurchaseBillRepo extends JpaRepository<PurchaseBillVO, Long> {

    @Query(nativeQuery = true, value = "select * from purchase_bill where purchasebill_id=?1")
    PurchaseBillVO getPurchaseBillById(Long id);

    @Query(nativeQuery = true, value = "select * from purchase_bill where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
    List<PurchaseBillVO> getPurchaseBillByOrgId(Long orgId, Long branchId);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getPurchaseBillDocId(Long orgId, String screenCode);
}