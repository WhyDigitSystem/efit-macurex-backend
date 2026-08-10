package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesReturnDTO {

    private Long id;

   
    private String docNo;
    private LocalDate docDate;

   
    private Long branch;

   
    private Long belongsTo;

   
    private String invoiceNo;
    private LocalDate invoiceDate;

    
    private String customerInvoiceNo;
    private LocalDate customerInvoiceDate;

    private String gatePassNo;

   
    private Long customer;

   
    private Long location;

   
    private Long returnType;

   
    private String approvedByAccounts;

   
    private String currency;

    
    private BigDecimal exchangeRate;

    
    private String referenceNo;
    private LocalDate referenceDate;

    private Long invoiceReferenceTypeId;

   
    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private Boolean active;

    private Boolean cancel;

    private String screenCode;

    private String screenName;

   
}