package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_contract")
public class PurchaseContractVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchasecontractgen")
    @SequenceGenerator(name = "purchasecontractgen", sequenceName = "purchasecontractseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchasecontract_id")
    private Long id;

    // Plant ID (Belongs To) -> linked to Branch (same pattern as Quotation/TransportBill "plant")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch")
    private BranchVO branch;

    // Contract No -> auto generated (prefix + running number) via DocumentTypeMappingDetails,
    // exactly like Department/Designation/Employee docId generation (screenCode = "PC")
    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    // Department -> linked to ListOfValuesDetails (List Of Values master, "DEPARTMENT" category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private ListOfValuesDetailsVO department;

    // Supplier Code / Supplier Name -> both come from the same Party/Customer master record (Supplier)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private CustomerVO supplier;

    @Column(name = "supplier_ref_no")
    private String supplierRefNo;

    @Column(name = "ref_date")
    private LocalDate refDate;

    // GST State -> auto-populated from Supplier's GST State (party master) at save time,
    // stored against GSTStateMaster so it can also be independently viewed/edited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gst_state_id")
    private GSTStateMasterVO gstState;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    // Derived at save-time: true when supplier's GST State country = India, else false (import)
    @Column(name = "is_igst_appl")
    private Boolean isIgstAppl;

    // Derived at save-time from Supplier's Country (party master): "LOCAL" if India else "IMPORT"
    @Column(name = "po_type")
    private String poType;

    // ---------------- 3. Charges Summary (single set of entry fields per contract) ----------------
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

    @Column(name = "insurance_amount")
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

    // ---------------- audit / org fields ----------------
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
    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseContractDetailsVO> purchaseContractDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseContractTaxDetailsVO> purchaseContractTaxDetailsVO = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseContractAttachmentVO> purchaseContractAttachmentVO = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active != null && active;
    }

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}