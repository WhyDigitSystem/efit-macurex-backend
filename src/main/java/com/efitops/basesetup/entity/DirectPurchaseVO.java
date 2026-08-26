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
@Table(name = "direct_purchase_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectPurchaseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direct_purchase_basicgen")
	@SequenceGenerator(name = "direct_purchase_basicgen", sequenceName = "direct_purchase_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "direct_purchase_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "gst_state")
	private GSTStateMasterVO gstState;

	@Column(name = "belongs_to")
	private String belongsTo;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "inv_date")
	private LocalDate invDate;

	@Column(name = "is_igst_applicable")
	private String isIgstApplicable;

	@Column(name = "issue_to")
	private String issueTo;

	@Column(name = "gstn_no")
	private String gstnNo;

	@Column(name = "inv_no")
	private String invNo;

	@Column(name = "supp_type")
	private String suppType;

	@Column(name = "dealer_type")
	private String dealerType;

	@ManyToOne
	@JoinColumn(name = "item_category")
	private ItemMasterVO itemCategory;

	@Column(name = "ecc_no_st_no")
	private String eccNoStNo;

	@Column(name = "is_reverse_charge")
	private String isReverseCharge;

	@Column(name = "basic_amount", precision = 18, scale = 2)
	private BigDecimal basicAmount = BigDecimal.ZERO;

	@Column(name = "discount", precision = 18, scale = 2)
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "after_discount_total_amount", precision = 18, scale = 2)
	private BigDecimal afterDiscountTotalAmount = BigDecimal.ZERO;

	@Column(name = "total_amount", precision = 18, scale = 2)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "DirectPurchase";

	@Column(name = "screen_code")
	private String screenCode = "DP";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "directPurchaseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DirectPurchaseCashDetailsVO> directPurchaseCashDetailsVO;

	@OneToMany(mappedBy = "directPurchaseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DirectPurchaseTaxDetailsVO> directPurchaseTaxDetailsVO;

	@OneToMany(mappedBy = "directPurchaseVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DirectPurchaseFileUploadDetailsVO> directPurchaseFileUploadDetailsVO;

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