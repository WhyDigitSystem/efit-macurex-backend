package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id                -> null on create, sent back by client on update
 * branch            -> [USER SELECT] Plant ID dropdown (BranchVO)
 * billNo            -> [SYSTEM SET] auto-generated on create, same pattern as Contract No/PB No
 * belongsTo         -> [USER ENTER] plain text
 * billDate          -> [USER ENTER] calendar field
 * department        -> [USER SELECT] List Of Values dropdown, same as Purchase Contract
 * supplier          -> [USER SELECT] Supplier Code / Supplier Name both resolve from SAME CustomerVO id
 * purchaseIndentId  -> [USER SELECT] from existing Purchase Indent module - indentNo/indentDate
 *                      snapshotted server-side once selected (see resolveIndentSelection)
 * gatePassNo        -> [USER ENTER] BLOCKED - "gateinverseentry api" module doesn't exist yet
 * supplierInvNo     -> [USER ENTER] BLOCKED - same as above
 * excisable         -> [USER SELECT] Yes/No toggle
 * date              -> [USER ENTER] calendar field
 * location          -> [USER ENTER] plain text
 * currency          -> [AUTO] resolved server-side from supplier (same placeholder logic as
 *                      PurchaseDropdownService.getSupplierById - BLOCKED real source)
 * taxCode           -> [USER SELECT] TaxDefinitionVO id (Tax Definition master, per your correction earlier)
 * purchaseDetails   -> [USER GRID ENTRY] "Purchase Detail" - Item Code/Description/Unit auto-fill
 *                      from ItemMaster (sourced from the selected Indent's item lines); Qty/Rate/
 *                      Discount user entered, Amount/Total Amount calculated (see service)
 * taxDetails        -> [USER GRID ENTRY] "Tax details" - Particulars/Amount/Ledger Account user
 *                      entered, Ledger Account -> List Of Values (same as Purchase Bill Tax Grid)
 * totalAmount       -> [USER ENTER or CALCULATED - see service note]
 * amountInWords     -> [USER ENTER]
 * paymentTerms      -> [USER ENTER]
 * deliveryTerms     -> [USER ENTER]
 * narration         -> [USER ENTER]
 * approved          -> [USER SELECT] Yes/No toggle
 * notes             -> [USER ENTER]
 * freight           -> [USER ENTER]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectPurchaseDTO {

    private Long id;
    private Long branch;
    private String belongsTo;
    private LocalDate billDate;
    private Long department;
    private Long supplier;

    private Long purchaseIndentId;

    private String gatePassNo;
    private String supplierInvNo;
    private Boolean excisable;
    private LocalDate date;
    private String location;
    private Long taxCode;

    private List<DirectPurchaseDetailsDTO> purchaseDetails;
    private List<DirectPurchaseTaxDetailsDTO> taxDetails;

    private BigDecimal totalAmount;
    private String amountInWords;
    private String paymentTerms;
    private String deliveryTerms;
    private String narration;
    private Boolean approved;
    private String notes;
    private BigDecimal freight;

    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}