package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractDTO {

    private Long id;

    private Long branch;
    
    private String belongsTo;
   
    private Long department;
    
    private Long supplier;

    private Long gstState;
    
    private String isIGSTAppl;

    private LocalDate validFrom;

    private LocalDate validTo;
    
    private String purchaseOrderType;
    
    private Long currency;

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
    
    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private String createdBy;
    

//    private List<PurchaseContractDetailsDTO> details;
//    private List<PurchaseContractTaxDetailsDTO> taxDetails;

   
}