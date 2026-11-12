package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractDTO {

    private Long id;

    private Long branch;
    
    private Long department;

    private String belongsTo;

    private LocalDate validFrom;

    private LocalDate validTo;

    private Long customer;

    private String contractFor;

    private Long gstState;

    private boolean isIgstApplicable;

    private LocalDate deliveryDate;

    private String taxType;

    private Long serviceName;

    private Long hsnSacCode;

    private boolean scrap;

    private BigDecimal taxPercentage;

    private BigDecimal discount;

    private String paymentsTerms;

    private String deliveryTerms;

    private BigDecimal freight;

    private String freightType;

    private String packingType;

    private BigDecimal insurance;

    private String modeOfDespatch;

    private BigDecimal inlandCharge;

    private Long preparedBy;

    private Long authoriedBy;

    private String narration;


    // Common Fields

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String cancelRemarks;

    private boolean active;
    
    
 // =========================
    // ITEM DETAILS
    // =========================

    private List<SupplierRateContractItemDetailsDTO> supplierRateContractItemDetailsDTO;


    // =========================
    // TAX DETAILS
    // =========================

    private List<SupplierRateContractTaxDetailsDTO> supplierRateContractTaxDetailsDTO;

}
