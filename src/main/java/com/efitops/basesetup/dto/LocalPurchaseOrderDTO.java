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
 * id                 -> null on create, sent back by client on update
 * branch             -> [USER SELECT] Plant ID dropdown (BranchVO), same as Purchase Contract "plant"
 * poNo               -> [SYSTEM SET] auto-generated on create (DocumentTypeMappingDetails, screen code "LPO")
 * belongsTo          -> [USER ENTER] plain text
 * poDate             -> [USER ENTER] calendar field
 * department         -> [USER SELECT] List Of Values
 * supplier           -> [USER SELECT] Supplier Code / Supplier Name resolve from SAME CustomerVO id
 * gstState           -> [AUTO, READ-ONLY] resolved server-side from supplier.getGstState(), same as Purchase Contract
 * supplierRefNo      -> [USER ENTER]
 * address            -> [USER ENTER] (or [AUTO] from supplier address - left user-entered for now, can be
 *                        defaulted from supplier on the frontend)
 * isIgstAppl         -> [AUTO, DERIVED] same as Purchase Contract - true when supplier's country != India
 * suppRefDt          -> [USER ENTER] calendar field
 * gstnNo             -> [AUTO] pulled from supplier, same as Purchase Bill
 * taxCode            -> [USER SELECT] List Of Values
 * isReverseChrg      -> [USER SELECT] Yes/No toggle
 * itemType           -> [USER SELECT] "Regular" or "Consumables"
 * indentRequired     -> [USER SELECT] Yes/No toggle
 * dealerType         -> [USER SELECT] List Of Values
 *
 * details            -> [USER GRID ENTRY] "1-PO Detail". Indent No/Indent Date/Indent Qty/Pending Indent Qty
 *                        are resolved server-side from the selected Purchase Indent line (indentDetailId).
 *                        Pending Indent Qty = that indent line's qty minus what's already been placed on
 *                        other Local Purchase Orders against the same indent line - always server-calculated.
 * taxDetails         -> [USER GRID ENTRY] "2-Tax Details", same shape as Purchase Contract tax details
 * attachments        -> [USER UPLOAD] "3-Quotation Attachment", same pattern as Purchase Contract attachments
 *
 * freightType/packingType/insurance/freight/totalAmount/modeOfDespatch/paymentTerms/deliveryTerms/
 * amountInWords/remarks/notes/preparedBy/checkedBy/authorisedBy -> [USER FORM ENTRY] "4-Terms And Conditions",
 *        flattened directly on this DTO, same pattern as Purchase Contract's terms fields
 *
 * orgId/financialYear/createdBy -> [SYSTEM/SESSION] passed from logged-in user context
 * -----------------------------------------------------------------------
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderDTO {

    private Long id;

    private Long branch;
    private String belongsTo;
    private LocalDate poDate;

    private Long department;

    private Long supplier;
    private String supplierRefNo;
    private String address;
    private LocalDate suppRefDt;

    private Long taxCode;
    private Boolean isReverseChrg;

    private String itemType; // "Regular" / "Consumables"
    private Boolean indentRequired;
    private Long dealerType;

    private List<LocalPurchaseOrderDetailsDTO> details;
    private List<LocalPurchaseOrderTaxDetailsDTO> taxDetails;

    // -------- 4. Terms And Conditions --------
    private String freightType;
    private String packingType;
    private BigDecimal insurance;
    private BigDecimal freight;
    private BigDecimal totalAmount;
    private String modeOfDespatch;
    private String paymentTerms;
    private String deliveryTerms;
    private String amountInWords;
    private String remarks;
    private String notes;
    private Long preparedBy;
    private Long checkedBy;
    private Long authorisedBy;

    // -------- audit / org --------
    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}