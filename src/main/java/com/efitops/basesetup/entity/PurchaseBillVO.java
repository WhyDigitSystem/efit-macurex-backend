package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill")
public class PurchaseBillVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasebillgen")
    @SequenceGenerator(name = "purchasebillgen", sequenceName = "purchasebillseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasebill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchVO plant;

    // PB No -> auto generated (prefix + running number), same pattern as Contract No / Doc No
    @Column(name = "pb_no")
    private String pbNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "pb_date")
    private LocalDate pbDate;

    // Supplier Code / Supplier Name / Supplier ID -> all come from the same Party/Customer master record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    // GST State -> auto pulled from Supplier's GST State, same as Purchase Contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gst_state_id")
    private GSTStateMasterVO gstState;

    // TODO: GRN module doesn't exist yet - once it does, replace these two plain
    // fields with a @ManyToOne to the real GRN entity (see PurchaseContract's
    // poType/poId pattern in PurchaseDeliveryScheduleVO for how to wire it in).
    @Column(name = "grn_no")
    private String grnNo;

    @Column(name = "grn_date")
    private LocalDate grnDate;

    // Derived at save-time from supplier's country, same as Purchase Contract
    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    @Column(name = "excisable")
    private Boolean excisable;

    // Currency -> List Of Values dropdown, same pattern as Dealer Type/Tax Code/etc below
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private ListOfValuesDetailsVO currency;

    // GSTN No -> auto pulled from supplier's GSTN field
    @Column(name = "gstn_no")
    private String gstnNo;

    @Column(name = "vendor_dc_no")
    private String vendorDcNo;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_type_id")
    private ListOfValuesDetailsVO dealerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_code_id")
    private ListOfValuesDetailsVO taxCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_purchase_order_id")
    private LocalPurchaseOrderVO localPurchaseOrder;

    @Column(name = "po_type")
    private String poType;   // "PURCHASE_CONTRACT" for now (LOCAL_PURCHASE_ORDER later)

    @Column(name = "po_id")
    private Long poId;

    // snapshot of the PO's number/date at selection time
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

    @Column(name = "duty_per_unit")
    private BigDecimal dutyPerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_category_id")
    private ListOfValuesDetailsVO postingCategory;

    @Column(name = "modvat_copy_received")
    private Boolean modvatCopyReceived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecc_type_id")
    private ListOfValuesDetailsVO eccType;

    @Column(name = "supplier_dc_inv_no")
    private String supplierDcInvNo;

    @Column(name = "supplier_dc_inv_date")
    private LocalDate supplierDcInvDate;

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
    private Long createdBy;

    @Column(name = "modified_by")
    private Long updatedBy;

    // ---------------- children ----------------
    @OneToMany(mappedBy = "purchaseBillVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseBillDetailsVO> purchaseBillDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseBillVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseBillTaxGridVO> purchaseBillTaxGridVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}