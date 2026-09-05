
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
@Table(name = "purchase_return_basic")
public class PurchaseReturnVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_return_basicgen")
	@SequenceGenerator(name = "purchase_return_basicgen", sequenceName = "purchase_return_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "purchase_return_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "belongs_to")
	private String belongsTo;

	@Column(name = "doc_date")
	private LocalDate docDate=LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "supplier")
	private CustomerVO supplier;

	@Column(name = "grn_no")
	private String grnNo;

	@Column(name = "grn_date")
	private LocalDate grnDate;

	@Column(name = "is_igst_appl")
	private String isIgstAppl;

	@Column(name = "excisable")
	private Boolean excisable;

	@Column(name = "vendor_dc_no")
	private String vendorDcNo;

	@Column(name = "exchange_rate", precision = 10, scale = 2)
	private BigDecimal exchangeRate;

	@Column(name = "dealer_type")
	private String dealerType;

	@Column(name = "purchaseorder_number")
	private String purchaseorderNumber;

	@Column(name = "purchaseorder_type")
	private String purchaseorderType;

	@Column(name = "purchaseorder_date")
	private LocalDate purchaseorderDate;

	@Column(name = "is_reverse_chrg")
	private Boolean isReverseChrg;

	@Column(name = "voucher_posting_date")
	private LocalDate voucherPostingDate;

	@Column(name = "duty_per_unit", precision = 10, scale = 2)
	private BigDecimal dutyPerUnit;

	@Column(name = "modvat_copy_received")
	private Boolean modvatCopyReceived;

	@Column(name = "supplier_dc_inv_no")
	private String supplierDcInvNo;

	@Column(name = "supplier_dc_inv_date")
	private LocalDate supplierDcInvDate;

	@Column(name = "amount_in_words")
	private String amountInWords;

	@Column(name = "entry_tax_applicable")
	private Boolean entryTaxApplicable;

	@Column(name = "narration")
	private String narration;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "total_freight", precision = 10, scale = 2)
	private BigDecimal totalFreight;

	@Column(name = "total_qty", precision = 10, scale = 2)
	private BigDecimal totalQty;

	@Column(name = "basic_value", precision = 10, scale = 2)
	private BigDecimal basicValue;

	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;

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

	@Column(name = "screen_code")
	private String screenCode = "PR";

	@Column(name = "screen_name")
	private String screenName = "PURCHASE RETURN";
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "purchaseReturnVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<PurchaseReturnDetailsVO> purchaseReturnDetailsVO = new ArrayList<>();

	@OneToMany(mappedBy = "purchaseReturnVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<PurchaseReturnTaxDetailsVO> purchaseReturnTaxDetailsVO = new ArrayList<>();

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