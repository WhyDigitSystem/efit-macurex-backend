package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_rejection_invoice_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesRejectionInvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_rejection_invoice_basicseq")
	@SequenceGenerator(name = "sales_rejection_invoice_basicseq", sequenceName = "sales_rejection_invoice_basicseq", allocationSize = 1, initialValue = 1000000001)
	@Column(name = "sales_rejection_invoice_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	// =========================
	// COMMON FIELDS
	// =========================

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "location")
	private LocationVO location;

	@ManyToOne
	@JoinColumn(name = "belongs_To")
	private ListOfValuesDetailsVO belongsTo;

	@Column(name = "vehicle")
	private String vehicle;

	@Column(name = "doc_type")
	private String docType;

	@Column(name = "is_igst_appl")
	private boolean isIgstAppl;

	@Column(name = "time_of_issue")
	private String timeOfIssue;

	@Column(name = "date_of_issue")
	private String dateOfIssue;

//	@Column(name = "invoice_date")
//	private LocalDate invoiceDate;

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;

//	@Column(name = "invoice_type")
//	private String invoiceType;

	@ManyToOne
	@JoinColumn(name = "currency")
	private CurrencyVO currency;

	@Column(name = "schedule_no")
	private String scheduleNo;

	@Column(name = "dispatch_instruction_no")
	private String dispatchInstructionNo;

	@Column(name = "time_of_removal")
	private String timeOfRemoval;

	@Column(name = "date_of_removal")
	private String dateOfRemoval;

	@Column(name = "schedule_date")
	private LocalDate scheduleDate;

	@Column(name = "dispatch_instruction_date")
	private LocalDate dispatchInstructionDate;

	@Column(name = "exchange_rate")
	private String exchangeRate;

	@Column(name = "month_year")
	private String monthYear;

	@Column(name = "kanban_card_no")
	private String kanbanCardNo;

	@Column(name = "excisable")
	private boolean excisable;

	@Column(name = "stock_posting")
	private boolean stockPosting;

	// =========================
	// SALES INVOICE FIELDS
	// =========================

//    @Column(name = "sales_invoice_no")
//    private String salesInvoiceNo;

	// =========================
	// DC CUM INVOICE FIELDS
	// =========================

//    @Column(name = "customer_type")
//    private String customerType;
//
//    @Column(name = "party_gst_state")
//    private String partyGstState;

//    @Column(name = "pdi_no")
//    private String pdiNo;

	// =========================
	// REJECTION INVOICE FIELDS
	// =========================

//    @Column(name = "rejection_invoice_no")
//    private String rejectionInvoiceNo;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "ref_date")
	private LocalDate refDate;

	@Column(name = "supplier_invoice_no")
	private String supplierInvoiceNo;

	// =========================
	// COMMON HEADER FIELDS
	// =========================

	@Column(name = "total_insurance")
	private BigDecimal totalInsurance;

	@Column(name = "total_freight")
	private BigDecimal totalFreight;

	@Column(name = "total_ass_val")
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
	private LocalDate purchaseOrderDate;

	@Column(name = "narration")
	private String narration;

	// =========================
	// DC CUM INVOICE SPECIFIC
	// =========================

	@Column(name = "tcs_amount")
	private BigDecimal tcsAmount;

	@Column(name = "net_weight")
	private BigDecimal netWeight;

	@Column(name = "gross_weight")
	private BigDecimal grossWeight;
	
	@Column(name = "financial_year")
	private String financialYear;
	
	@Column(name = "org_id")
	private Long orgId;
	@Column(name = "active")
	private boolean active;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	
	@Column(name = "screen_name")
	private String screenName="SALES REJECTION INVOICE";
	@Column(name = "screen_code")
	private String screenCode="SRI";

	@OneToMany(mappedBy = "salesRejectionInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesRejectionInvoiceDetailsVO> details = new ArrayList<>();
	
	@OneToMany(mappedBy = "salesRejectionInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesRejectionInvoiceTaxDetailsVO> taxDetails = new ArrayList<>();

}