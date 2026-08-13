// LocalPurchaseOrderRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.LocalPurchaseOrderVO;

@Repository
public interface LocalPurchaseOrderRepo extends JpaRepository<LocalPurchaseOrderVO, Long> {

    @Query(nativeQuery = true, value = "select * from local_purchase_order where localpurchaseorder_id=?1")
    LocalPurchaseOrderVO getLocalPurchaseOrderById(Long id);

    @Query(nativeQuery = true, value = "select * from local_purchase_order where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<LocalPurchaseOrderVO> getLocalPurchaseOrderByOrgId(Long orgId, Long branch);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getLocalPurchaseOrderDocId(Long orgId, String screenCode);
}