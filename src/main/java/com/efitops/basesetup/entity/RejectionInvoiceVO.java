package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "rejection_invoice_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectionInvoiceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejection_invoice_basicgen")
	@SequenceGenerator(name = "rejection_invoice_basicgen", sequenceName = "rejection_invoice_basicgen", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "rejection_invoice_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "month_year")
	private String monthYear;

	@Column(name = "belongs_to")
	private String belongsTo;

	@Column(name = "doc_type")
	private String docType;

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;

	@ManyToOne
	@JoinColumn(name = "di_no")
	private DespatchInstructionVO diNo;

	@Column(name = "stock_posting")
	private String stockPosting;

	@Column(name = "excisable")
	private String excisable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location")
	private LocationVO location;

	@Column(name = "vehicle")
	private String vehicle;

	@Column(name = "time_of_issue")
	private LocalTime timeOfIssue = LocalTime.now();

	@Column(name = "time_of_issue_date")
	private LocalDate timeOfIssueDate = LocalDate.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency")
	private CurrencyVO currency;

	@Column(name = "time_of_removal")
	private LocalTime timeOfRemoval = LocalTime.now();;

	@Column(name = "time_of_removal_date")
	private LocalDate timeOfRemovalDate = LocalDate.now();;

	@Column(name = "kanban_card_no")
	private String kanbanCardNo;

	@Column(name = "invoice_type")
	private String invoiceType;

	@Column(name = "sch_no")
	private String schNo;

	@Column(name = "sch_date")
	private LocalDate schDate;

	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;

	@Column(name = "total_insurance")
	private BigDecimal totalInsurance;

	@Column(name = "total_freight", precision = 10, scale = 2)
	private BigDecimal totalFreight;

	@Column(name = "total_ass_val", precision = 10, scale = 2)
	private BigDecimal totalAssVal;

	@Column(name = "mode_of_transport")
	private String modeOfTransport;

	@Column(name = "net_amount")
	private BigDecimal netAmount;

	@Column(name = "amount_in_words")
	private String amountInWords;

	@Column(name = "delivery_to")
	private String deliveryTo;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "purchase_order")
	private String purchaseOrder;

	@Column(name = "purchase_order_date")
	private String purchaseOrderDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "is_igst_applicable")
	private String isIgstApplicable;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "OTHER SALES INVOICE";

	@Column(name = "screen_code")
	private String screenCode = "OSI";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "rejectionInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OtherSalesInvoiceDetailsVO> otherSalesInvoiceDetailsVO;

	@OneToMany(mappedBy = "RejectionInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OtherSalesInvoiceTaxDetailsVO> otherSalesInvoiceTaxDetailsVO;

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
