package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    
    @Column(name = "doc_id")
    private String docId;

   
    @Column(name = "doc_date")
    private LocalDate docDate=LocalDate.now();

   
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

   
    @Column(name = "belongs_to")
    private String belongsTo;

  
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
    @JoinColumn(name = "customer")
    private CustomerVO customer;

   
    @ManyToOne
    @JoinColumn(name = "location")
    private LocationVO location;

    @Column(name = "date")
    private LocalDate date=LocalDate.now();
   
    @Column(name = "return_type")
    private String returnType;

    
    @Column(name = "approved_by_accounts")
    private String approvedByAccounts;

   
    @ManyToOne
    @JoinColumn(name = "currency")
    private CurrencyVO currency;

    @ManyToOne
    @JoinColumn(name = "exchange_rate")
    private DailyExchangeRateVO exchangeRate;

    @Column(name = "invoice_reference_type")
    private String invoiceReferenceType;
    
    @Column(name = "is_igst_applicable")
    private boolean isIgstApplicable;
    
    //summary charges
    
    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "narration")
    private String narration;

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
    
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
    
  

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
    
    
    @OneToMany(
            mappedBy = "salesReturn",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SalesReturnDetailsVO> salesReturnDetails = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "salesReturn",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SalesReturnTaxDetailsVO> salesReturnTaxDetails;

   
}