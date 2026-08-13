package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseBillVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebillgen")
    @SequenceGenerator(
            name = "purchasebillgen",
            sequenceName = "purchasebillseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasebill_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "pb_no")
    private String pbNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "pb_date")
    private LocalDate pbDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne
    @JoinColumn(name = "gst_state_id")
    private GSTStateMasterVO gstState;

    @Column(name = "grn_no")
    private String grnNo;

    @Column(name = "grn_date")
    private LocalDate grnDate;

    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    @Column(name = "excisable")
    private Boolean excisable;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private ListOfValuesDetailsVO currency;

    @Column(name = "gstn_no")
    private String gstnNo;

    @Column(name = "vendor_dc_no")
    private String vendorDcNo;

    @Column(name = "exchange_rate", precision = 10, scale = 2)
    private BigDecimal exchangeRate;

    @ManyToOne
    @JoinColumn(name = "dealer_type_id")
    private ListOfValuesDetailsVO dealerType;

    @ManyToOne
    @JoinColumn(name = "tax_code_id")
    private ListOfValuesDetailsVO taxCode;

    @ManyToOne
    @JoinColumn(name = "local_purchase_order_id")
    private LocalPurchaseOrderVO localPurchaseOrder;

    @Column(name = "po_type")
    private String poType;

    @Column(name = "po_id")
    private Long poId;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @Column(name = "is_reverse_chrg")
    private Boolean isReverseChrg;

    @Column(name = "voucher_posting_date")
    private LocalDate voucherPostingDate;

    @Column(name = "bill_date")
    private LocalDate date;

    @Column(name = "duty_per_unit", precision = 10, scale = 2)
    private BigDecimal dutyPerUnit;

    @ManyToOne
    @JoinColumn(name = "posting_category_id")
    private ListOfValuesDetailsVO postingCategory;

    @Column(name = "modvat_copy_received")
    private Boolean modvatCopyReceived;

    @ManyToOne
    @JoinColumn(name = "ecc_type_id")
    private ListOfValuesDetailsVO eccType;

    @Column(name = "supplier_dc_inv_no")
    private String supplierDcInvNo;

    @Column(name = "supplier_dc_inv_date")
    private LocalDate supplierDcInvDate;

    // ---------------- Charges Summary ----------------

    @Column(name = "total_freight", precision = 10, scale = 2)
    private BigDecimal totalFreight;

    @Column(name = "total_qty", precision = 10, scale = 2)
    private BigDecimal totalQty;

    @Column(name = "basic_value", precision = 10, scale = 2)
    private BigDecimal basicValue;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "entry_tax_applicable")
    private Boolean entryTaxApplicable;

    @Column(name = "narration")
    private String narration;

    @Column(name = "payment_terms")
    private String paymentTerms;

    // ---------------- Audit / Organization ----------------

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
    private Long createdBy;

    @Column(name = "modified_by")
    private Long updatedBy;

    // ---------------- Children ----------------

    @OneToMany(
            mappedBy = "purchaseBillVO",
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseBillDetailsVO> purchaseBillDetailsVO = new ArrayList<>();

    @OneToMany(
            mappedBy = "purchaseBillVO",
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseBillTaxGridVO> purchaseBillTaxGridVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    @Builder.Default
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}