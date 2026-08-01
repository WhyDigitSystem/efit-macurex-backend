package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDTO {

    private Long id;

    private String customerContractNo;

    private LocalDate contractDate;

    private Long branch;

    private String belongsTo;

    private String contractType;

    private String withQuotation;

    private String invoiceType;

    private Long customer;

    private String quotationNo;

    private LocalDate quotationDate;

    private String customerPoNo;

    private LocalDate customerPoDate;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;
    
    private String isIgstApplicable;

    private String postRate;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String cancelRemarks;

    private boolean active;
    
    private List<SalesContractDetailsDTO> details;
    
}
