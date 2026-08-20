
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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_contract_basic")
public class PurchaseContractVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_contract_basicgen")
    @SequenceGenerator(name = "purchase_contract_basicgen", sequenceName = "purchase_contract_basicseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "purchase_contract_basic_id")
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "department")
    private DepartmentVO department;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private CustomerVO supplier;
    
    @ManyToOne
    @JoinColumn(name = "gst_state")
    private GSTStateMasterVO GSTState;
    
    @Column(name = "is_igst_appl")
    private String isIGSTAppl;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "purchase_order_type")
    private String purchaseOrderType;
    
    @ManyToOne
    @JoinColumn(name = "currency")
    private CurrencyVO currency;
    

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

   
    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PurchaseContractDetailsVO> purchaseContractDetailsVO = new ArrayList<>();
//
//    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<PurchaseContractTaxDetailsVO> purchaseContractTaxDetailsVO = new ArrayList<>();
//
//    @OneToMany(mappedBy = "purchaseContractVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<PurchaseContractAttachmentVO> purchaseContractAttachmentVO = new ArrayList<>();

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