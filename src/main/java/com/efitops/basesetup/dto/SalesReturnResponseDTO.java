package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesReturnCustomerResponseDTO;

import lombok.Data;

@Data
public class SalesReturnResponseDTO {

    private Long id;

    // Document
    private String docNo;
    private LocalDate docDate;

    // Plant
    private BranchResponseDTO branch;
    

    // Belongs To
    private ListOfVlauesDetailsResponseDTO belongsTo;
  

    // Invoice
    private String invoiceNo;
    private LocalDate invoiceDate;

    // Customer Invoice
    private String customerInvoiceNo;
    private LocalDate customerInvoiceDate;

    
    private String gatePassNo;

  
    private SalesReturnCustomerResponseDTO customer;
   

    
    private LocationResponseDTO location;



    
    private ListOfVlauesDetailsResponseDTO returnType;
   
   
    private String approvedByAccounts;

  
    private String currency;

   
    private BigDecimal exchangeRate;

   
    private String referenceNo;
    private LocalDate referenceDate;

   
    private Long invoiceReferenceTypeId;
    private String invoiceReferenceType;

    
    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private boolean active;

    private boolean  cancel;

    private String screenCode;

    private String screenName;

   

}