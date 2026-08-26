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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill_basic")
public class PurchaseBillVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_bill_basicgen")
    @SequenceGenerator(name = "purchase_bill_basicgen", sequenceName = "purchase_bill_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_bill_basic_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private CustomerVO supplier;

    @ManyToOne
    @JoinColumn(name = "gst_state")
    private GSTStateMasterVO gstState;

    @Column(name = "grn_no")
    private String grnNo;

    @Column(name = "grn_date")
    private LocalDate grnDate;

    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    @Column(name = "excisable")
    private Boolean excisable;


    @Column(name = "vendor_dc_no")
    private String vendorDcNo;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;
    
    @Column(name = "purchaseorder_number")
    private String purchaseorderNumber;

    @Column(name = "purchaseorder_type")
    private String purchaseorderType;  

    @Column(name = "purchaseorder_date")
    private LocalDate purchaseorderDate;

    @Column(name = "is_reverse_chrg")
    private Boolean isReverseChrg;

    @Column(name = "voucher_posting_date")
    private LocalDate voucherPostingDate;

    @Column(name = "bill_date")
    private LocalDate date;

    @Column(name = "duty_per_unit")
    private BigDecimal dutyPerUnit;

//    @ManyToOne
//    @JoinColumn(name = "posting_category")
//    private ListOfValuesDetailsVO postingCategory;

    @Column(name = "modvat_copy_received")
    private Boolean modvatCopyReceived;

    @Column(name = "supplier_dc_inv_no")
    private String supplierDcInvNo;

    @Column(name = "supplier_dc_inv_date")
    private LocalDate supplierDcInvDate;
    
//    import purchase bill
    
    @Column(name = "credit_acc")
    private String creditAcc;
    
    @ManyToOne
    @JoinColumn(name = "statutory_forms")
    private ListOfValuesDetailsVO statutoryForms;
    
    @Column(name = "supplier_inv_value")
    private String supplierInvValue;
    
    // ---------------- 3. Charges Summary ----------------
    @Column(name = "total_freight")
    private BigDecimal totalFreight;

    @Column(name = "total_qty")
    private BigDecimal totalQty;

    @Column(name = "basic_value")
    private BigDecimal basicValue;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "entry_tax_applicable")
    private Boolean entryTaxApplicable;

    @Column(name = "narration")
    private String narration;

    @Column(name = "payment_terms")
    private String paymentTerms;

    // ---------------- audit / org ----------------
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;
    
    @Column(name = "screen_code")
    private String screenCode = "PB";

    @Column(name = "screen_name")
    private String screenName = "PURCHASE BILL";

    // ---------------- children ----------------
//    @OneToMany(mappedBy = "purchaseBillVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<PurchaseBillDetailsVO> purchaseBillDetailsVO = new ArrayList<>();
//
//    @OneToMany(mappedBy = "purchaseBillVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<PurchaseBillTaxGridVO> purchaseBillTaxGridVO = new ArrayList<>();

    @JsonGetter("active")
   	public String getActive() {
   		return active ? "Active" : "In-Active";
   	}

   	@JsonGetter("cancel")
   	public String getCancel() {
   		return cancel ? "T" : "F";
   	}
    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}