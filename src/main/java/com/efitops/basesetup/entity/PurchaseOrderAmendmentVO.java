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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseorder_amendment_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseorder_amendment_basicgen")
	@SequenceGenerator(name = "purchaseorder_amendment_basicgen", sequenceName = "purchaseorder_amendment_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchaseorder_amendment_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;

	@Column(name = "purchase_order_number")
	private String purchaseordernumber;

	@ManyToOne
	@JoinColumn(name = "currency")
	private CurrencyVO currency;

//	@Column(name = "ref_no")
//	private String refNo;
//
//	@Column(name = "ref_date")
//	private String refDate;
	
	@ManyToOne
	@JoinColumn(name = "exchange_rate")
	private DailyExchangeRateVO exchangeRate;

	
	@Column(name = "revision_no")
	private int revisionNo;

	@Column(name = "active")
	private boolean active;

	// summary

	@Column(name = "freight_type")
	private String freightType;

	@Column(name = "packing_type")
	private String packingType;

	@Column(name = "insurance_amount")
	private BigDecimal insuranceAmount;

	@Column(name = "mode_of_despatch")
	private String modeOfDespatch;

	@Column(name = "tax_description")
	private String taxDescription;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "PURCHASEORDERAMENDMENT";
	@Column(name = "screen_code")
	private String screenCode = "POA";

	@OneToMany(mappedBy = "purchaseOrderAmendmentVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderAmendmentDetailsVO> details = new ArrayList<>();

	@OneToMany(mappedBy = "purchaseOrderAmendmentVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PurchaseOrderAmendmentAttachmentVO> attachments = new ArrayList<>();

	@JsonGetter("activeStatus")
	public String getActiveStatus() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
