package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponseDTO {
	private Long id;
	private String docId;
	private LocalDate docDate;
	private LocalDate orderPlacedDate;
	private String poType;
	private String belongsTo;
	private String isIgstApplicable;
	private String isReverseCharge;
	private String itemType;
	private String indentRequired;
	private String active;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private String termsAndConditions;
	private String remarks;
	private String freightType;
	private String packingType;
	private String insurance;
	private String freight;
	private String modeOfDespatch;
	private String paymentTerms;
	private String deliveryTerms;
	private String notes;
	private String preparedBy;
	private String checkedBy;
	private String authorisedBy;
	private BigDecimal totalAmount;
	private String amountInWord;

	private BranchResponseDTO branch;
	private DepartmentResponseDTO department;
	private SupplierResponseDTO supplierCode;

	//

	// Import Specific Fields
	private String shipMode;
	private BigDecimal exchangeRate;
	private String portOfLoading;
	private String incoterm;
	private String foreCloseNo;
	private String countryOfOrigin;
	private String portOfDischarge;

	// Value Fields
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

	private CurrencyResponseDTO currency;
	private LmeResponseDTO lmeRate;

	private List<PurchaseOrderImportDetailsResponseDTO> purchaseOrderImportDetailsResponseDTO;

	private List<PurchaseOrderLocalDetailsResponseDTO> purchaseOrderLocalDetailsResponseDTO;
	private List<PurchaseOrderLocalTaxDetailsResponseDTO> purchaseOrderLocalTaxDetailsResponseDTO;
	private List<PurchaseOrderLocalFileUploadDetailsResponseDTO> purchaseOrderLocalFileUploadDetailsResponseDTO;

}
