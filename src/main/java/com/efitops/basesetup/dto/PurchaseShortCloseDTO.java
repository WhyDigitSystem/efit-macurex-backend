package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WHERE EACH FIELD'S DATA COMES FROM
 * -----------------------------------------------------------------------
 * id                    -> null on create, sent back by client on update
 * branch                -> [USER SELECT] Plant ID dropdown (BranchVO) - same as other modules
 * shortCloseNo          -> [SYSTEM SET] auto-generated on create (DocumentTypeMappingDetails, screen code "SC")
 * belongsTo             -> [USER ENTER] plain text, same pattern as Purchase Delivery Schedule
 * shortCloseDate        -> [USER ENTER] calendar field
 * type                  -> [USER ENTER] plain text
 * supplier              -> [USER SELECT] Supplier Code / Supplier Name both resolve from SAME CustomerVO id
 * poType / poId / poNo  -> [USER SELECT] ** PENDING - Local Purchase / PO module not built yet.
 *                           Mirrors the poType/poId pattern already used in Purchase Delivery Schedule
 *                           and Purchase Bill. Commented out below and in the service until that module exists.
 * referenceForShortClose-> [USER ENTER] free text reason/reference
 * details               -> [USER GRID ENTRY] "1-Order Closed Detail" grid.
 *                           ** PENDING - once Local Purchase/PO module exists, orderedQty/suppliedQty should be
 *                           auto-fetched by matching poId against that module's item lines instead of being
 *                           passed in by the client. For now they are accepted directly on each detail line.
 *                           pendingQty and shortCloseQty are always server-calculated, never trusted from client.
 * orgId/financialYear/createdBy -> [SYSTEM/SESSION] passed from logged-in user context
 * -----------------------------------------------------------------------
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseShortCloseDTO {

    private Long id;

    private Long branch;
    private String belongsTo;
    private LocalDate shortCloseDate;
    private String type;

    private Long supplier;

//    // PO/Del.Sch. No -> Local Purchase Order module not built yet.
//    // Once available, wire this the same way poType/poId works in
//    // PurchaseDeliveryScheduleDTO / PurchaseBillDTO.
//    private String poType;   // "PURCHASE_CONTRACT" / "LOCAL_PURCHASE_ORDER" / "DELIVERY_SCHEDULE"
//    private Long poId;
private Long localPurchaseOrderId; // -> the PO/Del.Sch. being short-closed
    private String referenceForShortClose;

    private List<PurchaseShortCloseDetailsDTO> details;

    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}