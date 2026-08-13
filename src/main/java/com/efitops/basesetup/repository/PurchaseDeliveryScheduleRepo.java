package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;

@Repository
public interface PurchaseDeliveryScheduleRepo extends JpaRepository<PurchaseDeliveryScheduleVO, Long> {

    @Query(nativeQuery = true, value = "select * from purchase_delivery_schedule where purchasedeliveryschedule_id=?1")
    PurchaseDeliveryScheduleVO getPurchaseDeliveryScheduleById(Long id);

    @Query(nativeQuery = true, value = "select * from purchase_delivery_schedule where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<PurchaseDeliveryScheduleVO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branchId);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getPurchaseDeliveryScheduleDocId(Long orgId, String screenCode);
}