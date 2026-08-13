// PurchaseShortCloseRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseShortCloseVO;

@Repository
public interface PurchaseShortCloseRepo extends JpaRepository<PurchaseShortCloseVO, Long> {

    @Query(nativeQuery = true, value = "select * from purchase_short_close where purchaseshortclose_id=?1")
    PurchaseShortCloseVO getPurchaseShortCloseById(Long id);

    @Query(nativeQuery = true, value = "select * from purchase_short_close where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
    List<PurchaseShortCloseVO> getPurchaseShortCloseByOrgId(Long orgId, Long branchId);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getPurchaseShortCloseDocId(Long orgId, String screenCode);
}