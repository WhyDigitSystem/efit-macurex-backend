// PurchaseBillRepo.java
package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;

@Repository
public interface PurchaseBillRepo extends JpaRepository<PurchaseBillVO, Long> {

    @Query(nativeQuery = true, value = "select * from purchase_bill where purchasebill_id=?1")
    PurchaseBillVO getPurchaseBillById(Long id);

    @Query(nativeQuery = true, value = "select * from purchase_bill_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<PurchaseBillVO> getPurchaseBillByOrgId(Long orgId, Long branch);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
            + "from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getPurchaseBillDocId(Long orgId, String screenCode);
    
    @Query(value = """
            SELECT 
                c.customer_name,
                c.customer_code,
                c.customer_id,
                c.ecc_type,
                c.is_gst_applicable,
                c.gst_no,
                c.gst_type,
                c.gst_state AS gst_state_id,
                g.state_code,
                g.state_name
            FROM customer_header c
            LEFT JOIN gststatemaster g
                   ON c.gst_state = g.gststatemaster_id
            WHERE c.org_id = :orgId
              AND c.branch = :branch
              AND c.customer_type = 'SUPPLIER'
            """, nativeQuery = true)
    List<Object[]> getSuppliersForPurchaseBill(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

}