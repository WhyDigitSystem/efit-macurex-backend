package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_return")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_return_gen")
    @SequenceGenerator(name = "sales_return_gen",sequenceName = "sales_return_seq",initialValue = 1000000001, allocationSize = 1)
    @Column(name = "sales_return_id")
    private Long id;

    // Document Number (Auto)
    @Column(name = "doc_no")
    private String docNo;

    // Date
    @Column(name = "doc_date")
    private LocalDate docDate;

    // Plant ID
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchVO branch;

    // Belongs To (Dropdown)
    @ManyToOne
    @JoinColumn(name = "belongs_to")
    private ListOfValuesDetailsVO belongsTo;

    // Invoice No (DC Invoice)
    @Column(name = "invoice_no")
    private String invoiceNo;

    // Invoice Date (Auto Fill)
    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    // Customer Invoice No
    @Column(name = "customer_invoice_no")
    private String customerInvoiceNo;

    // Customer Invoice Date
    @Column(name = "customer_invoice_date")
    private LocalDate customerInvoiceDate;

    // Gate Pass No
    @Column(name = "gate_pass_no")
    private String gatePassNo;

    // Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerVO customer;

    // Location
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationVO location;

    // Return Type
    @ManyToOne
    @JoinColumn(name = "return_type")
    private ListOfValuesDetailsVO returnType;

    // Approval By Accounts
    @Column(name = "approved_by_accounts")
    private String approvedByAccounts;

    // Currency
    @Column(name = "currency")
    private String currency;

    // Exchange Rate
    @Column(name = "exchange_rate", precision = 18, scale = 2)
    private BigDecimal exchangeRate;

    // Reference No
    @Column(name = "reference_no")
    private String referenceNo;

    // Reference Date
    @Column(name = "reference_date")
    private LocalDate referenceDate;

    // Invoice Reference Type
    @ManyToOne
    @JoinColumn(name = "invoice_reference_type")
    private ListOfValuesDetailsVO invoiceReferenceType;

    // Organization Details
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