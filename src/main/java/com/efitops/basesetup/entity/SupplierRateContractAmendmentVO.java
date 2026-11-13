package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "supplier_rate_contract_amendment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_rate_contract_amendmentgen")
    @SequenceGenerator(
            name = "supplier_rate_contract_amendmentgen",
            sequenceName = "supplier_rate_contract_amendmentseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "supplier_rate_contract_amendment_id")
    private Long id;

    // Plant Id
    @ManyToOne
    @JoinColumn(name = "branch")
    private BranchVO branch;

    // Amendment No
    @Column(name = "doc_id")
    private String docId;

    // Amendment Date
    @Column(name = "doc_date")
    private LocalDate docDate;

    // Belongs To
    @Column(name = "belongs_to")
    private String belongsTo;

    // Party Name
    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerVO customer;

    // Contract Date
    @Column(name = "contract_date")
    private LocalDate contractDate;

    // Contract No
    @Column(name = "contract_no")
    private String contractNo;

    // Valid From
    @Column(name = "valid_from")
    private LocalDate validFrom;

    // New Valid From
    @Column(name = "new_valid_from")
    private LocalDate newValidFrom;

    // Valid To
    @Column(name = "valid_to")
    private LocalDate validTo;

    // New Valid To
    @Column(name = "new_valid_to")
    private LocalDate newValidTo;

    // Revision No
    @Column(name = "revision_no")
    private String revisionNo;

    // Freight Type
    @Column(name = "freight_type")
    private String freightType;

    // Packing Type
    @Column(name = "packing_type")
    private String packingType;

    // Insurance Amount
    @Column(name = "insurance_amount")
    private BigDecimal insuranceAmount;

    // Mode Of Despatch
    @Column(name = "mode_of_despatch")
    private String modeOfDespatch;

    // Tax Description
    @Column(name = "tax_description")
    private String taxDescription;

    // Prepared By
    @ManyToOne
    @JoinColumn(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    // Authorised By
    @ManyToOne
    @JoinColumn(name = "authorised_by")
    private EmployeeMasterVO authorisedBy;

    // Remarks
    @Column(name = "remarks")
    private String remarks;

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

    @Column(name = "screen_name")
    private String screenName = "SUPPLIER RATE CONTRACT AMENDMENT";

    @Column(name = "screen_code")
    private String screenCode = "SRCA";

    @OneToMany(
            mappedBy = "supplierRateContractAmendmentVO",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<SupplierRateContractAmendmentItemDetailsVO> itemDetails;

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
