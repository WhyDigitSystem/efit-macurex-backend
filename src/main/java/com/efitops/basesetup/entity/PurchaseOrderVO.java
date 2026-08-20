package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.efitops.basesetup.dto.PoType;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchase_order_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_basicgen")
	@SequenceGenerator(name = "purchase_order_basicgen", sequenceName = "purchase_order_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_order_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "order_placed_date")
	private LocalDate orderPlacedDate = LocalDate.now();

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "po_type")
	@Enumerated
	private PoType poType;

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@ManyToOne
	@JoinColumn(name = "supplier_code")
	private CustomerVO supplierCode;

	@Column(name = "is_igst_applicable")
	private String isIgstApplicable;

	@Column(name = "is_reverse_charge")
	private String isReverseCharge;

	@Column(name = "item_type")
	private String itemType;

	@Column(name = "indent_required")
	private String indentRequired;

	// Import

	@ManyToOne
	@JoinColumn(name = "currency")
	private CurrencyVO currency;

	@Column(name = "ship_mode")
	private String shipMode;

	@Column(name = "exchange_rate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@ManyToOne
	@JoinColumn(name = "lme_rate")
	private LMEVO lmeRate;

	@Column(name = "port_of_loading")
	private String portOfLoading;

	@Column(name = "incoterm")
	private String incoterm;

	@Column(name = "foreclose_no")
	private String foreCloseNo;

	@Column(name = "country_of_origin")
	private String countryOfOrigin;

	@Column(name = "port_of_discharge")
	private String portOfDischarge;

	// commonfileds

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "OrderAcceptance";

	@Column(name = "screen_code")
	private String screenCode = "OA";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	// Local terms And Condtions

	@Column(name = "terms_and_conditions")
	private String termsAndConditions;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "freight_type")
	private String freightType;

	@Column(name = "packing_type")
	private String packingType;

	@Column(name = "insurance")
	private String insurance;

	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@Column(name = "delivery_terms")
	private String deliveryTerms;

	@Column(name = "mode_of_despatch")
	private String modeOfDespatch;

	@Column(name = "notes")
	private String notes;
	
	@Column(name = "freight")
	private String freight;

	// import

	@Column(name = "total_fob_value_fc", precision = 10, scale = 2)
	private BigDecimal totalFobValueFc;

	@Column(name = "total_fob_value_inr", precision = 10, scale = 2)
	private BigDecimal totalFobValueInr;

	@Column(name = "freight_fc", precision = 10, scale = 2)
	private BigDecimal freightFc;

	@Column(name = "freight_inr", precision = 10, scale = 2)
	private BigDecimal freightInr;

	@Column(name = "insurance_fc", precision = 10, scale = 2)
	private BigDecimal insuranceFc;

	@Column(name = "insurance_inr", precision = 10, scale = 2)
	private BigDecimal insuranceInr;

	@Column(name = "other_charges_fc", precision = 10, scale = 2)
	private BigDecimal otherChargesFc;

	@Column(name = "other_charges_inr", precision = 10, scale = 2)
	private BigDecimal otherChargesInr;

	@Column(name = "total_po_value_fc", precision = 10, scale = 2)
	private BigDecimal totalPoValueFc;

	@Column(name = "bank_charges", precision = 10, scale = 2)
	private BigDecimal bankCharges;

	@Column(name = "packing_charges", precision = 10, scale = 2)
	private BigDecimal packingCharges;

	@Column(name = "sur_charges", precision = 10, scale = 2)
	private BigDecimal surCharges;

	@Column(name = "total_po_value_inr", precision = 10, scale = 2)
	private BigDecimal totalPoValueInr;

	@Column(name = "amount_in_word")
	private String amountInWord;

	@Column(name = "prepared_by")
	private String preparedBy;

	@Column(name = "checked_by")
	private String checkedBy;

	@Column(name = "authorised_by")
	private String authorisedBy;

	//

	// purchaseLocal

	@OneToMany(mappedBy = "purchaseOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderLocalDetailsVO> purchaseOrderLocalDetailsVO;

	@OneToMany(mappedBy = "purchaseOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderLocalTaxDetailsVO> purchaseOrderLocalTaxDetailsVO;

	@OneToMany(mappedBy = "purchaseOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderLocalFileUploadDetailsVO> purchaseOrderLocalFileUploadDetailsVO;

	// import

	@OneToMany(mappedBy = "purchaseOrderVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderImportDetailsVO> purchaseOrderImportDetailsVO;

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
