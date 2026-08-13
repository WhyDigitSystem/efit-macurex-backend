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
 * branch             -> [USER SELECT] Plant ID dropdown (BranchVO) - same as Purchase Contract "plant"
 * pbNo               -> [SYSTEM SET] auto-generated on create, same pattern as Contract No (DocumentTypeMappingDetails)
 * belongsTo          -> [USER ENTER] plain text, same as Purchase Delivery Schedule
 * pbDate             -> [USER ENTER] calendar field
 * supplier           -> [USER SELECT] Supplier Code / Supplier Name / Supplier ID all resolve from SAME CustomerVO id
 * gstState           -> [AUTO, READ-ONLY] resolved server-side from supplier.getGstState(), same as Purchase Contract
 * grnNo              -> [USER SELECT?] ** NEEDS CONFIRMATION - is this typed free text, or selected from an
 *                        existing GRN/goods-receipt record? If selected, what master/table is it?
 * grnDate            -> [USER ENTER] calendar field (or [AUTO] if grnNo pulls from a GRN record - TBC)
 * isIgstAppl         -> [AUTO, DERIVED] same as Purchase Contract - true when supplier's country != India
 * excisable          -> [USER SELECT] Yes/No toggle, plain boolean
 * currency           -> [USER SELECT] ** NEEDS CONFIRMATION - dropdown from a Currency master? which table?
 * gstnNo              -> [AUTO] ** likely pulled from supplier (party master GSTN field) - CONFIRM supplier has this
 * vendorDcNo         -> [USER ENTER]
 * exchangeRate       -> [USER ENTER]
 * dealerType         -> [USER SELECT] ** NEEDS CONFIRMATION - List Of Values category, or separate master?
 * taxCode            -> [USER SELECT] ** NEEDS CONFIRMATION - List Of Values category, or TaxDefinition?
 * poType / poNo      -> [USER SELECT] mirrors Purchase Delivery Schedule's PO selection - user picks a
 *                        Purchase Contract (or, later, a Local Purchase Order); poNo/poDate snapshotted server-side
 * isReverseChrg      -> [USER SELECT] Yes/No toggle, plain boolean
 * voucherPostingDate -> [USER ENTER] calendar field
 * date               -> [USER ENTER] calendar field ** NEEDS CONFIRMATION - what is this distinct from pbDate/voucherPostingDate?
 * dutyPerUnit        -> [USER ENTER]
 * postingCategory    -> [USER SELECT] ** NEEDS CONFIRMATION - List Of Values category, or separate master?
 * modvatCopyReceived -> [USER SELECT] Yes/No toggle, plain boolean
 * eccType            -> [USER SELECT] ** NEEDS CONFIRMATION - List Of Values category, or separate master?
 * supplierDcInvNo    -> [USER ENTER]
 * supplierDcInvDate  -> [USER ENTER] calendar field
 *
 * purchaseDetails    -> [USER GRID ENTRY] "1-Purchase Detail" - Item Code/Description/HSN/Tax Type/Tax%/Unit
 *                        auto-fill from ItemMaster (same as Purchase Contract's details grid); qty/rate/amount
 *                        fields are user entered or server calculated (see grid DTO)
 * taxGrid            -> [USER GRID ENTRY] "2-Tax Grid" - Ledger Account Name needs a Chart of Accounts / Ledger
 *                        master lookup - ** NEEDS CONFIRMATION which table
 * chargesSummary     -> [USER FORM ENTRY] "3-Charges Summary" fields, flattened directly on this DTO below
 *
 * orgId/financialYear/createdBy -> [SYSTEM/SESSION] passed from logged-in user context, same as every other master
 * -----------------------------------------------------------------------
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillDTO {

    private Long id;

    private Long branch;
    private String belongsTo;
    private LocalDate pbDate;

    private Long supplier;

    private String grnNo;
    private LocalDate grnDate;

    // NOTE: excisable, isReverseChrg, modvatCopyReceived sent as booleans (Yes/No toggles on UI)
    private Boolean excisable;

    private Long currency;
    private String vendorDcNo;
    private BigDecimal exchangeRate;
    private Long dealerType;
    private Long taxCode;
    private Long localPurchaseOrderId; // -> selected Local Purchase Order; poNo/poDate snapshotted server-side
    private String poType;   // "PURCHASE_CONTRACT" (or "LOCAL_PURCHASE_ORDER" later) - same as Purchase Delivery Schedule
    private Long poId;

    private Boolean isReverseChrg;
    private LocalDate voucherPostingDate;
    private LocalDate date;
    private BigDecimal dutyPerUnit;
    private Long postingCategory;
    private Boolean modvatCopyReceived;
    private Long eccType;
    private String supplierDcInvNo;
    private LocalDate supplierDcInvDate;

    // -------- 3. Charges Summary --------
    private BigDecimal totalFreight;
    private BigDecimal totalQty;
    private BigDecimal basicValue;
    private BigDecimal totalAmount;
    private String amountInWords;
    private Boolean entryTaxApplicable;
    private String narration;
    private String paymentTerms;

    // -------- children --------
    private List<PurchaseBillDetailsDTO> purchaseDetails;
    private List<PurchaseBillTaxGridDTO> taxGrid;

    // -------- audit / org --------
    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}