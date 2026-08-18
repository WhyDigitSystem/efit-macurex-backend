package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderImportResponseDTO {

    // Header Fields
    private Long id;
    private String docId;
    private LocalDate docDate;
    private LocalDate orderPlacedDate;
    private String poType;
    private String belongsTo;
    private String isIgstApplicable;
    private String isReverseCharge;
    private String itemType;
    private String indentRequired;
    private String active;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private String remarks;
    private String preparedBy;
    private String checkedBy;
    private String authorisedBy;

    // Import Specific Fields
    private String shipMode;
    private BigDecimal exchangeRate;
    private String paymentTerms;
    private String portOfLoading;
    private String incoterm;
    private String foreCloseNo;
    private String countryOfOrigin;
    private String portOfDischarge;

    // Value Fields
    private BigDecimal totalFobValueFc;
    private BigDecimal totalFobValueInr;
    private BigDecimal freightFc;
    private BigDecimal freightInr;
    private BigDecimal insuranceFc;
    private BigDecimal insuranceInr;
    private BigDecimal otherChargesFc;
    private BigDecimal otherChargesInr;
    private BigDecimal totalPoValueFc;
    private BigDecimal bankCharges;
    private BigDecimal packingCharges;
    private BigDecimal surCharges;
    private BigDecimal totalPoValueInr;
    private String amountInWord;

    private BranchResponseDTO branch;
    private DepartmentResponseDTO department;
    private SupplierResponseDTO supplierCode;
    private CurrencyResponseDTO currency;
    private LmeResponseDTO lmeRate;

    private List<PurchaseOrderImportDetailsResponseDTO> purchaseOrderImportDetailsResponseDTO;

}
