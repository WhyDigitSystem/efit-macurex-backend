package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_return_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_return_basicgen")
    @SequenceGenerator(name = "sales_return_basicgen",sequenceName = "sales_return_basicseq",initialValue = 1000000001, allocationSize = 1)
    @Column(name = "sales_return_basicid")
    private Long id;

    
    @Column(name = "doc_no")
    private String docNo;

   
    @Column(name = "doc_date")
    private LocalDate docDate;

   
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchVO branch;

   
    @ManyToOne
    @JoinColumn(name = "belongs_to")
    private ListOfValuesDetailsVO belongsTo;

  
    @Column(name = "invoice_no")
    private String invoiceNo;

    
    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    
    @Column(name = "customer_invoice_no")
    private String customerInvoiceNo;

   
    @Column(name = "customer_invoice_date")
    private LocalDate customerInvoiceDate;

   
    @Column(name = "gate_pass_no")
    private String gatePassNo;

    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerVO customer;

   
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationVO location;

   
    @ManyToOne
    @JoinColumn(name = "return_type")
    private ListOfValuesDetailsVO returnType;

    
    @Column(name = "approved_by_accounts")
    private String approvedByAccounts;

   
    @Column(name = "currency")
    private String currency;

   
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "reference_no")
    private String referenceNo;

   
    @Column(name = "reference_date")
    private LocalDate referenceDate;

    @ManyToOne
    @JoinColumn(name = "invoice_reference_type")
    private ListOfValuesDetailsVO invoiceReferenceType;

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "screen_code")
    private String screenCode = "SR";

    @Column(name = "screen_name")
    private String screenName = "SALESRETURN";
    
  

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    
    

   
}