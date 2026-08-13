package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating / updating a Purchase Contract.
 *
 * WHERE EACH FIELD'S DATA COMES FROM
 * -----------------------------------------------------------------------
 * id                -> null on create, sent back by client on update
 * branch            -> [USER SELECT] Plant ID (dropdown from /api/commonmaster/getBranchByOrgId), stored as "Belongs To" plant
 * contractDate      -> [USER ENTER] calendar field
 * department        -> [USER SELECT] dropdown sourced from ListOfValuesDetails ("DEPARTMENT" category), same api pattern as departmentapi
 * supplier          -> [USER SELECT] Supplier Code / Supplier Name dropdown -> both display from the SAME CustomerVO (party master) record id
 * supplierRefNo     -> [USER ENTER]
 * refDate           -> [USER ENTER] calendar field
 * gstState          -> [AUTO, READ-ONLY on UI] resolved server-side from supplier.getGstState() (comes from party master), not entered by user
 * validFrom/validTo -> [USER ENTER] calendar fields, contract header validity
 * isIgstAppl        -> [AUTO, DERIVED] server sets true/false based on supplier's GST State country == India
 * poType            -> [AUTO, DERIVED] server sets "LOCAL" if supplier's country == India else "IMPORT" (from party master's country)
 * details           -> [USER GRID ENTRY] section "1-Contract Details"
 * taxDetails        -> [USER GRID ENTRY] section "2-Tax Details"
 * chargesSummary    -> [USER FORM ENTRY] section "3-Charges Summary" fields, flattened directly on this DTO below
 * orgId/branchCode/financialYear/createdBy -> [SYSTEM/SESSION] passed from logged-in user context, same as every other master in this codebase
 * -----------------------------------------------------------------------
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractDTO {

    private Long id;

    // Plant ID (Belongs To) -> Branch dropdown
    private Long branch;

    private LocalDate contractDate;

    // Department dropdown -> ListOfValuesDetails id
    private Long department;

    // Supplier Code / Supplier Name -> both resolved from this single Party (Customer) id
    private Long supplier;

    private String supplierRefNo;

    private LocalDate refDate;

    // NOTE: gstState is NOT accepted from the client on create/update — it is auto-derived
    // from the supplier's GST State on the server. Included here only so the response can
    // echo it back; client should treat it as read-only.
    private Long gstState;

    private LocalDate validFrom;

    private LocalDate validTo;

    // -------- 3. Charges Summary (single set of entered fields) --------
    private String modeOfDespatch;
    private String paymentTerms;
    private String delivery;
    private String freightType;
    private String packingType;
    private BigDecimal insuranceAmount;
    private String bank;
    private String accounts;
    private String swiftCode;
    private String checkedBy;
    private String preparedBy;
    private String authorisedBy;
    private String freightForwarder;
    private String notes;
    private String termsConditions;

    // -------- children --------
    private List<PurchaseContractDetailsDTO> details;
    private List<PurchaseContractTaxDetailsDTO> taxDetails;

    // -------- audit / org (system supplied) --------
    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}