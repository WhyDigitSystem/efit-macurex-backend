package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesReturnDTO {

    private Long id;

    // Document
    private String docNo;
    private LocalDate docDate;

    // Plant
    private Long branchId;

    // Belongs To (LOV)
    private Long belongsToId;

    // Invoice
    private String invoiceNo;
    private LocalDate invoiceDate;

    // Customer Invoice
    private String customerInvoiceNo;
    private LocalDate customerInvoiceDate;

    // Gate Pass
    private String gatePassNo;

    // Customer
    private Long customerId;

    // Location
    private Long locationId;

    // Return Type (LOV)
    private Long returnTypeId;

    // Approved By Accounts
    private String approvedByAccounts;

    // Currency
    private String currency;

    // Exchange Rate
    private BigDecimal exchangeRate;

    // Reference
    private String referenceNo;
    private LocalDate referenceDate;

    // Invoice Reference Type (LOV)
    private Long invoiceReferenceTypeId;

    // Common Fields
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