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
public class PurchaseBillDTO {

	private Long id;

	private Long branch;
	private String belongsTo;
	private LocalDate docDate;


	private Long supplier;

	private String grnNo;
	private LocalDate grnDate;

	private boolean excisable;

	private String vendorDcNo;
	private BigDecimal exchangeRate;
	private Long dealerType;
	private String purchaseorderType;
	private Long purchaseorderId;

	private boolean isIgstApplicable;

	private boolean isReverseChrg;
	private LocalDate voucherPostingDate;
	private LocalDate date;
	private BigDecimal dutyPerUnit;
	private Long postingCategory;
	private Boolean modvatCopyReceived;
	private Long eccType;
	private String supplierDcInvNo;
	private LocalDate supplierDcInvDate;
	
	private Long currency;

//	---------import purchase bill
	private String creditAcc;
	private Long statutoryForms;
	private String supplierInvValue;

	// -------- audit / org --------
	private Long orgId;
	private String financialYear;
	private boolean active;
	private String cancelRemarks;
	private String createdBy;

	// -------- children --------
	private List<PurchaseBillDetailsDTO> purchaseDetails;
	private List<PurchaseBillTaxGridDTO> taxGrid;
	private List<PurchaseBillChargesSummaryDTO> billChargesSummaryDTO;
	private List<ImportPurchaseBillDetailsDTO> importPurchaseDetails;
	private List<ImportPurchaseBillTaxDetailsDTO> importPurchaseTax;
	private List<ImportPurchaseBillChargesSummaryDTO> importBillChargesSummaryDTO;
}