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
public class PurchaseOrderDTO {
	private Long id;

	private String poType;

	private String belongsTo;
	
	private LocalDate orderPlacedDate;

	private Long department;

	private Long supplierCode;

	private String isIgstApplicable;

	private String isReverseCharge;

	private String itemType;

	private String indentRequired;

	// Import

	private Long currency;

	private String shipMode;

	private BigDecimal exchangeRate;

	private String paymentTerms;

	private Long lmeRate;

	private String portOfLoading;

	private String incoterm;

	private String foreCloseNo;

	private String countryOfOrigin;

	private String portOfDischarge;

	// Common fields

	private String createdBy;

	private boolean active;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	// Local Terms And Conditions

	private String termsAndConditions;

	private String remarks;

	// Import

	private BigDecimal totalFobValueFc;

	private BigDecimal totalFobValueInr;

	private BigDecimal freightFc;

	private BigDecimal freightInr;

	private BigDecimal insuranceFc;

	private BigDecimal insuranceInr;

	private BigDecimal otherChargesFc;

	private BigDecimal otherChargesInr;

	private BigDecimal totalPoValueFc;

	private BigDecimal bankCharges;

	private BigDecimal packingCharges;

	private BigDecimal surCharges;

	private BigDecimal totalPoValueInr;

	private String amountInWord;

	private String preparedBy;

	private String checkedBy;

	private String authorisedBy;
	
	private String freightType;

	private String packingType;

	private String insurance;

	private String deliveryTerms;

	private String modeOfDespatch;

	private String notes;
	
	private String freight;

	// Purchase Local

	private List<PurchaseOrderLocalDetailsDTO> purchaseOrderLocalDetailsDTO;

	private List<PurchaseOrderLocalTaxDetailsDTO> purchaseOrderLocalTaxDetailsDTO;

	private List<PurchaseOrderLocalFileUploadDetailsDTO> purchaseOrderLocalFileUploadDetailsDTO;

	private List<PurchaseOrderImportDetailsDTO> purchaseOrderImportDetailsDTO;


}
