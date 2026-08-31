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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_rate_contract")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_rate_contractgen")
    @SequenceGenerator(
            name = "supplier_rate_contractgen",
            sequenceName = "supplier_rate_contractseq",
            initialValue = 1000000001,
            allocationSize = 1
    )
    @Column(name = "supplier_rate_contract_id")
    private Long id;

    @Column(name = "branch")
    private BranchVO branch;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "department")
    private DepartmentVO department;

    @Column(name = "doc_date")
    private LocalDate docDate = LocalDate.now();

    @Column(name = "belongs_to")
    private String belongsTo;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "customer")
    private CustomerVO customer;

    @Column(name = "contract_for")
    private String contractFor;

    @Column(name = "gst_state")
    private GSTStateMasterVO gstState;

    @Column(name = "is_igst_applicable")
    private boolean isIgstApplicable;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "service_name")
    private CustomerVO serviceName;

    @Column(name = "hsn_sac_code")
    private HsnVO hsnSacCode;

    @Column(name = "scrap")
    private boolean scrap;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "payments_terms")
    private String paymentsTerms;

    @Column(name = "delivery_terms")
    private String deliveryTerms;

    @Column(name = "freight")
    private BigDecimal freight;

    @Column(name = "freight_type")
    private ListOfValuesDetailsVO freightType;

    @Column(name = "packing_type")
    private ListOfValuesDetailsVO packingType;

    @Column(name = "insurance")
    private BigDecimal insurance;

    @Column(name = "mode_of_despatch")
    private String modeOfDespatch;

    @Column(name = "inland_charge")
    private BigDecimal inlandCharge;

    @Column(name = "prepared_by")
    private EmployeeMasterVO preparedBy;

    @Column(name = "authoried_by")
    private EmployeeMasterVO authoriedBy;

    @Column(name = "narration")
    private String narration;
    
    
    //common fields
    

    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String updatedBy;

    @Column(name = "cancel_remarks")
    private String cancelRemarks;

    @Column(name = "active")
    private boolean active;

    @Column(name = "cancel")
    private boolean cancel = false;

    @Column(name = "screen_code")
    private String screenCode = "SRC";

    @Column(name = "screen_name")
    private String screenName = "SUPPLIER RATE CONTRACT";
    
    @JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@OneToMany(
	        mappedBy = "supplierRateContractVO",
	        cascade = CascadeType.ALL
	)
	@JsonManagedReference
	private List<SupplierRateContractItemDetailsVO> supplierRateContractItemDetailsVO;
    
  

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
