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
@Table(name = "orderacceptance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderacceptancegen")
	@SequenceGenerator(name = "orderacceptancegen", sequenceName = "orderacceptanceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "orderacceptance_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "belongs_to")
	private String belongsTo;

	@Column(name = "so_type")
	private String soType;

	@Column(name = "with_quotation")
	private String withQuotation;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerVO customerId;

	@Column(name = "quotation_date")
	private LocalDate quotationDate;

	@Column(name = "quotation_no")
	private String quotationNo;

	@Column(name = "enquiry_no")
	private String enquiryNo;

	@Column(name = "enquiry_date")
	private LocalDate enquiryDate;

	@Column(name = "customer_purchase_order_no")
	private String customerPurchaseOrderNo;

	@Column(name = "customer_purchase_order_date")
	private LocalDate customerPurchaseOrderDate;

	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "post_rate")
	private String postRate;
	
	@Column(name = "customer_type")
	private String customerType;
	
	@Column(name = "gst_no")
	private String gstNo;
	
	@Column(name = "gst_approval")
	private String gstApproval;

	// Common Fields

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "updated_by")
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

	@Column(name = "destination")
	private String destination;

	@Column(name = "mode_of_transport")
	private String modeOfTransport;

	@Column(name = "gross_value", precision = 10, scale = 2)
	private BigDecimal grossalue;

	@Column(name = "freight")
	private String freight;

	@Column(name = "delivery_terms")
	private String deliveryTerms;

	@Column(name = "taxable_amount", precision = 10, scale = 2)
	private BigDecimal taxableAmount;
	
	@Column(name = "total_tax_amount", precision = 10, scale = 2)
	private BigDecimal totalTaxAmount;
	
	@Column(name = "total_discount_amount", precision = 10, scale = 2)
	private BigDecimal totalDiscountAmount;

	@Column(name = "payment_terms")
	private String paymentTerms;

	@Column(name = "specification")
	private String specification;

	@Column(name = "note")
	private String note;

	@OneToMany(mappedBy = "orderAcceptanceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderAcceptanceDetailsVO> orderAcceptanceDetailsVO;

	@OneToMany(mappedBy = "orderAcceptanceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderAcceptanceTaxDetailsVO> orderAcceptanceTaxDetailsVO;

	@OneToMany(mappedBy = "orderAcceptanceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OrderAcceptanceFileUploadDetailsVO> orderAcceptanceFileUploadDetailsVO;

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