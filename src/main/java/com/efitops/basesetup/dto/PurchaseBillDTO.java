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

	private Boolean excisable;

	private String vendorDcNo;
	private BigDecimal exchangeRate;
	private Long dealerType;
	private String purchaseorderType;
	private Long purchaseorderId;

	private Boolean isReverseChrg;
	private LocalDate voucherPostingDate;
	private LocalDate date;
	private BigDecimal dutyPerUnit;
	private Long postingCategory;
	private Boolean modvatCopyReceived;
	private Long eccType;
	private String supplierDcInvNo;
	private LocalDate supplierDcInvDate;

	// -------- 3. Charges Summary --------
	private BigDecimal totalFreight;
	private BigDecimal totalQty;
	private BigDecimal basicValue;
	private BigDecimal totalAmount;
	private String amountInWords;
	private Boolean entryTaxApplicable;
	private String narration;
	private String paymentTerms;

	// -------- audit / org --------
	private Long orgId;
	private String financialYear;
	private boolean active;
	private String cancelRemarks;
	private String createdBy;
	private String updatedBy;

	// -------- children --------
//	private List<PurchaseBillDetailsDTO> purchaseDetails;
//	private List<PurchaseBillTaxGridDTO> taxGrid;
}