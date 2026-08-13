package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderDetailsDTO {

    private Long id;

    // Link to the Purchase Indent line this PO row is fulfilling.
    // Indent No / Indent Date / Indent Qty / Pending Indent Qty are all resolved
    // server-side from this id - not sent by the client.
    private Long indentDetailId; // -> PurchaseIndentDetailsVO id (optional if indentRequired = false)

    private Long itemId;
    private String customerPartNo; // ** NEEDS CONFIRMATION - pulled from ItemMasterVO if that field exists, else user-entered
    private Long hsnId;            // optional override, else falls back to item's HSN
    private Long taxType;          // List Of Values

    private BigDecimal taxPercent;
    private Long purchaseUnitId;
    private Long primaryUnitId;    // optional override, else falls back to item's primary unit
    private BigDecimal conversionFactor; // Purchase Unit -> Primary Unit, same as Purchase Indent grid

    private BigDecimal poQtyInPurchaseUnit;

    private BigDecimal rateInInr;
    private BigDecimal discountPercent;
    private LocalDate deliveryDate;

    private BigDecimal sgstRate;
    private BigDecimal cgstRate;
    private BigDecimal igstRate;
}