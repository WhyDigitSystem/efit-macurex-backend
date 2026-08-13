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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direct_purchase")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directpurchasegen")
    @SequenceGenerator(
            name = "directpurchasegen",
            sequenceName = "directpurchaseseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "directpurchase_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private ListOfValuesDetailsVO department;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @ManyToOne
    @JoinColumn(name = "purchase_indent_id")
    private PurchaseIndentVO purchaseIndent;

    @Column(name = "indent_no")
    private String indentNo;

    @Column(name = "indent_date")
    private LocalDate indentDate;

    @Column(name = "gate_pass_no")
    private String gatePassNo;

    @Column(name = "supplier_inv_no")
    private String supplierInvNo;

    @Column(name = "excisable")
    private Boolean excisable;

    @Column(name = "bill_ref_date")
    private LocalDate date;

    @Column(name = "location")
    private String location;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "tax_code_id")
    private TaxDefinitionVO taxCode;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "delivery_terms")
    private String deliveryTerms;

    @Column(name = "narration")
    private String narration;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "notes")
    private String notes;

    @Column(name = "freight", precision = 10, scale = 2)
    private BigDecimal freight;

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
            mappedBy = "directPurchaseVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<DirectPurchaseDetailsVO> directPurchaseDetailsVO =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "directPurchaseVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<DirectPurchaseTaxDetailsVO> directPurchaseTaxDetailsVO =
            new ArrayList<>();

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