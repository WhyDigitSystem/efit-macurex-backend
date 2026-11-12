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
@Table(name = "local_purchase_order")
public class LocalPurchaseOrderVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseordergen")
    @SequenceGenerator(name = "localpurchaseordergen", sequenceName = "localpurchaseorderseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "localpurchaseorder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchVO plant;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private ListOfValuesDetailsVO department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gst_state_id")
    private GSTStateMasterVO gstState;

    @Column(name = "supplier_ref_no")
    private String supplierRefNo;

    @Column(name = "address", length = 1000)
    private String address;

    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    @Column(name = "supp_ref_dt")
    private LocalDate suppRefDt;

    @Column(name = "gstn_no")
    private String gstnNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_code_id")
    private ListOfValuesDetailsVO taxCode;

    @Column(name = "is_reverse_chrg")
    private Boolean isReverseChrg;

    @Column(name = "item_type")
    private String itemType; // "Regular" / "Consumables"

    @Column(name = "indent_required")
    private Boolean indentRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_type_id")
    private ListOfValuesDetailsVO dealerType;

    // ---------------- 4. Terms And Conditions ----------------
    @Column(name = "freight_type")
    private String freightType;

    @Column(name = "packing_type")
    private String packingType;

    @Column(name = "insurance")
    private BigDecimal insurance;

    @Column(name = "freight")
    private BigDecimal freight;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "mode_of_despatch")
    private String modeOfDespatch;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "delivery_terms")
    private String deliveryTerms;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "notes", length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checked_by")
    private EmployeeMasterVO checkedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorised_by")
    private EmployeeMasterVO authorisedBy;

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
    @OneToMany(mappedBy = "localPurchaseOrderVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LocalPurchaseOrderDetailsVO> localPurchaseOrderDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "localPurchaseOrderVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LocalPurchaseOrderTaxDetailsVO> localPurchaseOrderTaxDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "localPurchaseOrderVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LocalPurchaseOrderAttachmentVO> localPurchaseOrderAttachmentVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}