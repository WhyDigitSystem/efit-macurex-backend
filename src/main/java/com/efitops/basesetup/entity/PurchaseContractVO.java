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
@Table(name = "purchase_contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseContractVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasecontractgen")
    @SequenceGenerator(
            name = "purchasecontractgen",
            sequenceName = "purchasecontractseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "purchasecontract_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private ListOfValuesDetailsVO department;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @Column(name = "supplier_ref_no")
    private String supplierRefNo;

    @Column(name = "ref_date")
    private LocalDate refDate;

    @ManyToOne
    @JoinColumn(name = "gst_state_id")
    private GSTStateMasterVO gstState;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    @Column(name = "po_type")
    private String poType;

    // ---------------- Charges Summary ----------------

    @Column(name = "mode_of_despatch")
    private String modeOfDespatch;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "delivery")
    private String delivery;

    @Column(name = "freight_type")
    private String freightType;

    @Column(name = "packing_type")
    private String packingType;

    @Column(name = "insurance_amount", precision = 10, scale = 2)
    private BigDecimal insuranceAmount;

    @Column(name = "bank")
    private String bank;

    @Column(name = "accounts")
    private String accounts;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "checked_by")
    private String checkedBy;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "authorised_by")
    private String authorisedBy;

    @Column(name = "freight_forwarder")
    private String freightForwarder;

    @Column(name = "notes")
    private String notes;

    @Column(name = "terms_conditions")
    private String termsConditions;

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
            mappedBy = "purchaseContractVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseContractDetailsVO> purchaseContractDetailsVO = new ArrayList<>();

    @OneToMany(
            mappedBy = "purchaseContractVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseContractTaxDetailsVO> purchaseContractTaxDetailsVO = new ArrayList<>();

    @OneToMany(
            mappedBy = "purchaseContractVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<PurchaseContractAttachmentVO> purchaseContractAttachmentVO = new ArrayList<>();

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