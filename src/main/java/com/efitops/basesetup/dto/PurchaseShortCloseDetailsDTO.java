package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseShortCloseDetailsDTO {

    private Long id;

    private Long itemId;
    private Long unitId; // optional - falls back to item's primary unit if not sent

    // ** PENDING: once Local Purchase/PO module exists, these two should be looked up
    // server-side from that PO's item lines (matched by poId) instead of client-supplied.
    private BigDecimal orderedQty;
    private BigDecimal suppliedQty;

    private BigDecimal newRequiredQty;

    // pendingQty and shortCloseQty are NOT read from this DTO on save -
    // they are always recalculated server-side. Included here only so the
    // response DTO shape can reuse this class if needed.
    private BigDecimal pendingQty;
    private BigDecimal shortCloseQty;
}