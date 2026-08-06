package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesReturnResponseDTO {

    private Long id;

    // Document
    private String docNo;
    private LocalDate docDate;

    // Plant
    private Long branchId;
    private String branchName;

    // Belongs To
    private Long belongsToId;
    private String belongsTo;

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
    private String customerCode;
    private String customerName;

    // Auto Fill from Customer
    private String partyGSTState;
    private String gstNo;
    private String isIgstApplicable;

    // Location
    private Long locationId;
    private String locationCode;
    private String locationName;

    // Return Type
    private Long returnTypeId;
    private String returnType;

    // Approved By Accounts
    private String approvedByAccounts;

    // Currency
    private String currency;

    // Exchange Rate
    private BigDecimal exchangeRate;

    // Reference
    private String referenceNo;
    private LocalDate referenceDate;

    // Invoice Reference Type
    private Long invoiceReferenceTypeId;
    private String invoiceReferenceType;

    // Common Fields
    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;

    private String cancel;

    private String screenCode;

    private String screenName;

   

}