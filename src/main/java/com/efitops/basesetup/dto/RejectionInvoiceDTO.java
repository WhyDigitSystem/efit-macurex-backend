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
public class RejectionInvoiceDTO {

	private Long id;

	private String monthYear;

	private String belongsTo;

	private String docType;

	private Long customer;

	private Long diNo;

	private String stockPosting;

	private String excisable;

	private Long location;

	private String vehicle;

	private Long currency;

	private String kanbanCardNo;

	private String invoiceType;

	private String schNo;

	private LocalDate schDate;

	private BigDecimal exchangeRate;

	private BigDecimal totalInsurance;

	private BigDecimal totalFreight;

	private BigDecimal totalAssVal;

	private String modeOfTransport;

	private BigDecimal netAmount;

	private String deliveryTo;

	private String paymentTerms;

	private String purchaseOrder;

	private String purchaseOrderDate;

	private String createdBy;

	private boolean active;

	private String updatedBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private String isIgstApplicable;

	private List<OtherSalesInvoiceDetailsDTO> otherSalesInvoiceDetailsDTO;

	private List<OtherSalesInvoiceTaxDetailsDTO> otherSalesInvoiceTaxDetailsDTO;

}
