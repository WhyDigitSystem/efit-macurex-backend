// DirectPurchaseRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseRepo extends JpaRepository<DirectPurchaseVO, Long> {

    @Query(nativeQuery = true, value = "select * from direct_purchase where directpurchase_id=?1")
    DirectPurchaseVO getDirectPurchaseById(Long id);

    @Query(nativeQuery = true, value = "select * from direct_purchase where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<DirectPurchaseVO> getDirectPurchaseByOrgId(Long orgId, Long branch);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getDirectPurchaseDocId(Long orgId, String screenCode);
}