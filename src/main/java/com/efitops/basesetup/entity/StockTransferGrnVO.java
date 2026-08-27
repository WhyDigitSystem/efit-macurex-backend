package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "stock_transfer_grn_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferGrnVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_transfer_grn_basicgen")
	@SequenceGenerator(name = "stock_transfer_grn_basicgen", sequenceName = "stock_transfer_grn_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stock_transfer_grn_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;

	@ManyToOne
	@JoinColumn(name = "supplier_code")
	private CustomerVO supplierCode;

	@Column(name = "is_igst_applicable")
	private String isIgstApplicable;

	@Column(name = "is_reverse_charge")
	private String isReverseCharge;

	@Column(name = "gate_pass_no")
	private String gatePassNo;

	@Column(name = "po_no")
	private String poNo;

	@Column(name = "dealer_type")
	private String dealerType;

	@Column(name = "schedule_no")
	private String scheduleNo;

	@Column(name = "schedule_date")
	private LocalDate scheduleDate;

	@Column(name = "schedule_start_date")
	private LocalDate scheduleStartDate;

	@Column(name = "schedule_end_date")
	private LocalDate scheduleEndDate;

	@ManyToOne
	@JoinColumn(name = "currency")
	private CurrencyVO currency;

	@Column(name = "exchange_rate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;

	@Column(name = "grn_clear_time")
	private LocalTime grnClearTime = LocalTime.now();

	@Column(name = "gross_amount", precision = 10, scale = 2)
	private BigDecimal grossAmount;

	@Column(name = "modvat_copy_received")
	private String modvatCopyReceived;

	@Column(name = "total_qty_in_kg", precision = 10, scale = 2)
	private BigDecimal totalQtyInKg;

	@Column(name = "party_dc_no")
	private String partyDcNo;

	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;

	@Column(name = "supplier_dc_date")
	private String supplierDcDate;

	// Common Fields
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
	private String screenName = "StockTransferGrn";

	@Column(name = "screen_code")
	private String screenCode = "STG";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	// Import / Additional Fields
	@Column(name = "net_amount", precision = 10, scale = 2)
	private BigDecimal netAmount;

	@Column(name = "total_amount_tax", precision = 10, scale = 2)
	private BigDecimal totalAmountTax;

	@Column(name = "basic_amount", precision = 10, scale = 2)
	private BigDecimal basicAmount;

	@Column(name = "invoice_sent_on")
	private LocalDate invoiceSentOn;

	@Column(name = "remarks")
	private String remarks;

	@OneToMany(mappedBy = "stockTransferGrnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<StockTransferGrnDetailsVO> stockTransferGrnDetailsVO;

	@OneToMany(mappedBy = "stockTransferGrnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<StockTransferGrnFileUploadDetailsVO> stockTransferGrnFileUploadDetailsVO;

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