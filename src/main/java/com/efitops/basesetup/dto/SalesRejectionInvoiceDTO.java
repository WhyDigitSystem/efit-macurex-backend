package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceDTO {

	private Long id;

	// =========================
	// COMMON FIELDS
	// =========================

	private Long branch;

	private Long location;

	private Long belongsTo;

	private String vehicle;

	private String docType;

	private boolean isIgstApplicable;

	private String timeOfIssue;

	private String dateOfIssue;

//	private LocalDate invoiceDate;

	private Long customer;

//	private String invoiceType;

	private Long currency;

	private String scheduleNo;

	private String dispatchInstructionNo;

	private String timeOfRemoval;

	private String dateOfRemoval;

	private LocalDate scheduleDate;

	private LocalDate dispatchInstructionDate;

	private String exchangeRate;

	private String monthYear;

	private String kanbanCardNo;

	private boolean excisable;

	private boolean stockPosting;

	// =========================
	// REJECTION INVOICE FIELDS
	// =========================

	private String refNo;

	private LocalDate refDate;

	private String supplierInvoiceNo;

	// =========================
	// COMMON HEADER FIELDS
	// =========================

	private BigDecimal totalInsurance;

	private BigDecimal totalFreight;

	private BigDecimal totalAssVal;

	private String modeOfTransport;

	private BigDecimal netAmount;

	private String amountInWords;

	private String deliveryTo;

	private String paymentTerms;

	private String purchaseOrder;

	private LocalDate purchaseOrderDate;

	private String narration;

	// =========================
	// DC CUM INVOICE SPECIFIC
	// =========================

	private BigDecimal tcsAmount;

	private BigDecimal netWeight;

	private BigDecimal grossWeight;

	// =========================
	// AUDIT / COMMON
	// =========================

	private String createdBy;

	private Long orgId;

	private boolean active;

	private String cancelRemarks;
	private String financialYear;


	List<SalesRejectionInvoiceDetailsDTO> salesRejectionInvoiceDetailsDTO;
	List<SalesRejectionInvoiceTaxDetailsDTO> salesRejectionInvoiceTaxDetailsDTO;

}
