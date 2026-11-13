// LocalPurchaseOrderDetailsRepo.java
package com.efitops.basesetup.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.LocalPurchaseOrderDetailsVO;

@Repository
public interface LocalPurchaseOrderDetailsRepo extends JpaRepository<LocalPurchaseOrderDetailsVO, Long> {

    // Sum of Qty already placed on OTHER Local Purchase Orders against the same indent line,
    // used to compute Pending Indent Qty. Excludes the current PO being edited (excludePoId can be 0/null on create).
    @Query(nativeQuery = true, value =
            "select coalesce(sum(d.po_qty_in_purchase_unit),0) from local_purchase_order_details d " +
                    "join local_purchase_order p on p.localpurchaseorder_id = d.localpurchaseorder_id " +
                    "where d.indent_details_id = ?1 and p.cancel = 0 " +
                    "and (?2 is null or p.localpurchaseorder_id <> ?2)")
    BigDecimal getAlreadyOrderedQtyForIndentLine(Long indentDetailId, Long excludePoId);
}