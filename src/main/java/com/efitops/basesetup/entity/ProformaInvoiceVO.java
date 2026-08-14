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
@Table(name = "proforma_invoice_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proforma_invoice_basicgen")
	@SequenceGenerator(name = "proforma_invoice_basicgen", sequenceName = "proforma_invoice_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "proforma_invoice_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "belongs_to")
	private String belongsTo;

	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;

	@Column(name = "purchase_order_no")
	private String purchaseOrderNo;

	@Column(name = "purchase_order_date")
	private LocalDate purchaseOrderDate;

	@Column(name = "ref_no")
	private String refNo;

	@Column(name = "ref_date")
	private LocalDate refDate;

	@Column(name = "kind_attention")
	private String kindAttention;

	@Column(name = "designation")
	private String designation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location")
	private LocationVO location;

	@Column(name = "time_of_issue")
	private LocalTime timeOfIssue = LocalTime.now();

	@Column(name = "time_of_issue_date")
	private LocalDate timeOfIssueDate = LocalDate.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_name")
	private BankDetailsVO bankName;

	@Column(name = "insurance", precision = 10, scale = 2)
	private BigDecimal insurance;

	@Column(name = "freight", precision = 10, scale = 2)
	private BigDecimal freight;

	@Column(name = "no_of_pkg", precision = 10, scale = 2)
	private BigDecimal noOfPkg;

	@Column(name = "pkg_type")
	private String pkgType;

	@Column(name = "rate_of_duty", precision = 10, scale = 2)
	private BigDecimal rateOfDuty;

	@Column(name = "tariff_no")
	private String tariffNo;

	@Column(name = "basic_value", precision = 10, scale = 2)
	private BigDecimal basicValue;

	@Column(name = "gross_amount", precision = 10, scale = 2)
	private BigDecimal grossAmount;

	@Column(name = "mode_of_transport")
	private String modeOfTransport;

	@Column(name = "amount_in_words")
	private String amountInWords;

	@Column(name = "delivery_to")
	private String deliveryTo;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "payment_percentage")
	private String paymentPercentage;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "narration")
	private String narration;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "PROFORMAINVOICE";

	@Column(name = "screen_code")
	private String screenCode = "PI";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@OneToMany(mappedBy = "proformaInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProformaInvoiceDetailsVO> proformaInvoiceDetailsVO;

	@OneToMany(mappedBy = "proformaInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProformaInvoiceTaxDetailsVO> proformaInvoiceTaxDetailsVO;

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
