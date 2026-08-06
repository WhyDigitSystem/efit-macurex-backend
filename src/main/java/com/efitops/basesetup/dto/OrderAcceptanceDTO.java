package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceDTO {

	private Long id;
	private String orderNo;
	private String docId;
	private String belongsTo;

	private String soType;

	private String withQuotation;

	private LocalDate quotationDate;

	private String quotationNo;

	private String enquiryNo;

	private LocalDate enquiryDate;

	private String customerPurchaseOrderNo;

	private LocalDate customerPurchaseOrderDate;

	private String postRate;

	private Long customerId;

	// Common Fields

	private String createdBy;

	private String updatedBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branchId;

	private String destination;

	private String modeOfTransport;

	private BigDecimal grossalue;

	private String freight;

	private String deliveryTerms;

	private String paymentTerms;

	private String specification;

	private String note;
	
	private String gstApproval;

	private List<OrderAcceptanceDetailsDTO> orderAcceptanceDetailsDTO;

	private List<OrderAcceptanceTaxDetailsDTO> orderAcceptanceTaxDetailsDTO;

}
