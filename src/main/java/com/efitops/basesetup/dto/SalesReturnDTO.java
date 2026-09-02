package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesReturnDTO {

    private Long id;

   
    private Long branch;

   
    private String belongsTo;

   
    private String invoiceNo;
    private LocalDate invoiceDate;

    
    private String customerInvoiceNo;
    private LocalDate customerInvoiceDate;

    private String gatePassNo;

   
    private Long customer;

    private boolean isIgstApplicable;
   
    private Long location;

   
    private String returnType;

   
    private String approvedByAccounts;

   
    private Long currency;

    
    private Long exchangeRate;

    private String invoiceReferenceType;
    
    
    private BigDecimal netAmount;

    private String amountInWords;

    private String narration;

   
    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String cancelRemarks;

    private boolean active;

    private List<SalesReturnDetailsDTO> salesReturnDetails;
    
    private List<SalesReturnTaxDetailsDTO> salesReturnTaxDetails;

   
}