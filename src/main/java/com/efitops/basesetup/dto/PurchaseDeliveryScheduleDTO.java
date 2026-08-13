package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id            -> null on create, sent back by client on update
 * branch        -> [USER SELECT] Plant ID dropdown (BranchVO)
 * belongsTo     -> [USER ENTER] plain text field
 * docDate       -> [USER ENTER] calendar field
 * schStartDate  -> [USER ENTER] calendar field
 * schEndDate    -> [USER ENTER] calendar field
 * supplier      -> [USER SELECT] Supplier Code / Supplier Name -> both resolve from SAME CustomerVO id
 * poType/poId   -> [USER SELECT] chosen from getPoOptionsBySupplier() - "PURCHASE_CONTRACT" (live) or
 *                  "LOCAL_PURCHASE_ORDER" (not wired up yet)
 * poNo/poDate   -> NOT accepted from client - [AUTO] snapshotted server-side from the chosen PO
 * preparedBy    -> [USER ENTER]
 * note          -> [USER ENTER]
 * scheduleDetails -> [USER GRID ENTRY] "1-Schedule Details" (Item Code + 3 entry fields, rest auto from item)
 * schedule        -> [USER GRID ENTRY] "2-Schedule" (fully user entered)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDTO {

    private Long id;
    private Long branch;
    private String belongsTo;
    private LocalDate docDate;
    private LocalDate schStartDate;
    private LocalDate schEndDate;
    private Long supplier;

    private String poType;   // "PURCHASE_CONTRACT" or "LOCAL_PURCHASE_ORDER"
    private Long poId;

    private String preparedBy;
    private String note;

    private List<PurchaseDeliveryScheduleDetailsDTO> scheduleDetails;
    private List<PurchaseDeliveryScheduleLineDTO> schedule;

    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}