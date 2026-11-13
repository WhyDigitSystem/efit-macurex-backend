package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentDTO {

    private Long id;

    // Plant Id
    private Long branch;

    // Belongs To
    private String belongsTo;

    // Party Name
    private Long customer;

    // Contract Date
    private LocalDate contractDate;

    // Contract No
    private String contractNo;

    // Valid From
    private LocalDate validFrom;

    // New Valid From
    private LocalDate newValidFrom;

    // Valid To
    private LocalDate validTo;

    // New Valid To
    private LocalDate newValidTo;

    // Revision No
    private String revisionNo;

    // Freight Type
    private String freightType;

    // Packing Type
    private String packingType;

    // Insurance Amount
    private BigDecimal insuranceAmount;

    // Mode Of Despatch
    private String modeOfDespatch;

    // Tax Description
    private String taxDescription;

    // Prepared By
    private Long preparedBy;

    // Authorised By
    private Long authorisedBy;

    // Remarks
    private String remarks;

    private Long orgId;

    private String financialYear;

    private boolean active;

    private String cancelRemarks;

    private String createdBy;
    
    private List<SupplierRateContractAmendmentItemDetailsDTO> itemDetails;
}