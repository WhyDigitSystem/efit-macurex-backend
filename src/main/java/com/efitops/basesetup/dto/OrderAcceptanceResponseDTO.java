package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAcceptanceResponseDTO {

	private Long id;

	private String docId;

	private LocalDate docDate;

	private String orderNo;
	
	private String gstApproval;

	private String belongsTo;

	private String soType;

	private String withQuotation;

	private CustomerResponseGstDetailsDTO customerId;

	private LocalDate quotationDate;

	private String quotationNo;

	private String enquiryNo;

	private LocalDate enquiryDate;

	private String customerPurchaseOrderNo;

	private LocalDate customerPurchaseOrderDate;

	private String postRate;

	private String createdBy;

	private boolean active=true;

	private boolean cancel = false;

	private String updatedBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private BranchResponseDTO branch;

	private String destination;

	private String modeOfTransport;

	private BigDecimal grossalue;

	private String freight;

	private String deliveryTerms;

	private String paymentTerms;

	private String specification;

	private String note;

	private List<OrderAcceptanceDetailsResponseDTO> orderAcceptanceDetailsResponseDTO;

	private List<OrderAcceptanceTaxDetailsResponsDTO> orderAcceptanceTaxDetailsResponsVO;

	private List<OrderAcceptanceFileUploadDetailsDTO> orderAcceptanceFileUploadDetailsDTO;

}
