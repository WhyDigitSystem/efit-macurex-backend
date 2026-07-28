package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "customer_header")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "customerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "customer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_category")
	private ListOfValuesVO customerCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_category1")
	private ListOfValuesVO customerCategory1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_category2")
	private ListOfValuesVO customerCategory2;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplier_type")
	private ListOfValuesVO supplierType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_legal_name")
	private String customerLegalName;

	@Column(name = "trade_name")
	private String tradeName;

	@Column(name = "is_group_company")
	private boolean groupCompany;

	@Column(name = "zone")
	private String zone;

	@Column(name = "vendor_code")
	private String vendorCode;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "is_registered")
	private boolean registered;

	@Column(name = "is_excisable")
	private boolean excisable;

	@Column(name = "party_credit_limit", precision = 18, scale = 2)
	private BigDecimal partyCreditLimit;

	@Column(name = "party_credit_period")
	private int partyCreditPeriod;

	//normalization
	@Column(name = "belongs_to")
	private String belongsTo;

	@Column(name = "gst_type")
	private String gstType;

	@Column(name = "gst_no")
	private String gstNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gst_state")
	private GSTStateMasterVO gstState;
	
    @Column(name = "is_gst_applicable")
    private boolean gstApplicable;

    @Column(name = "address", length = 500)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    private CityVO city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
    private StateVO state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    private CountryVO country;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "email")
    private String email;

    @Column(name = "web_address")
    private String webAddress;

    @Column(name = "cin_no")
    private String cinNo;

    @Column(name = "over_due_interest", precision = 18, scale = 2)
    private BigDecimal overDueInterest;

    @Column(name = "introduced_by")
    private String introducedBy;

    @Column(name = "cst_no")
    private String cstNo;

    @Column(name = "ecc_no")
    private String eccNo;

    @Column(name = "ecc_type")
    private String eccType;

    @Column(name = "kst_no")
    private String kstNo;

    @Column(name = "phone")
    private String phone;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "customer_range")
    private String range;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "date_of_approval")
    private LocalDate dateOfApproval;

    @Column(name = "iso_status")
    private String isoStatus;

    @Column(name = "type_extent_of_control")
    private String typeExtentOfControl;

    @Column(name = "re_assessment_date")
    private LocalDate reAssessmentDate;

    @Column(name = "credit_period")
    private int creditPeriod;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "scope_of_supply")
    private String scopeOfSupply;

    @Column(name = "basis_of_approval")
    private String basisOfApproval;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_acc_no")
    private String bankAccountNo;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "ifsc_code")
    private String ifscCode;
    

	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "created_by", length = 25)
	private String createdBy;
	@Column(name = "modify_by", length = 25)
	private String updatedBy;
	@Column(name = "cancel_remarks", length = 150)
	private String cancelRemarks;
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "financial_year", length = 5)
	private String financialYear;
	@Column(name = "screen_code", length = 30)
	private String screenCode = "CUS";
	@Column(name = "screen_name", length = 30)
	private String screenName = "CUSTOMER";
	
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CustomerContactDetailsVO> customerContactDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "customerVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CustomerShippingDetailsVO> customerShippingDetails = new ArrayList<>();

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
