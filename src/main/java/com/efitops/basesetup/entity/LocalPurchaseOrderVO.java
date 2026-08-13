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
@Table(name = "local_purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalPurchaseOrderVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localpurchaseordergen")
    @SequenceGenerator(
            name = "localpurchaseordergen",
            sequenceName = "localpurchaseorderseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "localpurchaseorder_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "po_date")
    private LocalDate poDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private ListOfValuesDetailsVO department;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne
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

    @ManyToOne
    @JoinColumn(name = "tax_code_id")
    private ListOfValuesDetailsVO taxCode;

    @Column(name = "is_reverse_chrg")
    private Boolean isReverseChrg;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "indent_required")
    private Boolean indentRequired;

    @ManyToOne
    @JoinColumn(name = "dealer_type_id")
    private ListOfValuesDetailsVO dealerType;

    // ---------------- Terms And Conditions ----------------

    @Column(name = "freight_type")
    private String freightType;

    @Column(name = "packing_type")
    private String packingType;

    @Column(name = "insurance", precision = 10, scale = 2)
    private BigDecimal insurance;

    @Column(name = "freight", precision = 10, scale = 2)
    private BigDecimal freight;

    @Column(name = "total_amount", precision = 10, scale = 2)
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

    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @ManyToOne
    @JoinColumn(name = "checked_by")
    private EmployeeMasterVO checkedBy;

    @ManyToOne
    @JoinColumn(name = "authorised_by")
    private EmployeeMasterVO authorisedBy;

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
            mappedBy = "localPurchaseOrderVO",
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<LocalPurchaseOrderDetailsVO> localPurchaseOrderDetailsVO = new ArrayList<>();

    @OneToMany(
            mappedBy = "localPurchaseOrderVO",
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<LocalPurchaseOrderTaxDetailsVO> localPurchaseOrderTaxDetailsVO = new ArrayList<>();

    @OneToMany(
            mappedBy = "localPurchaseOrderVO",
            cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
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