package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturnResponseDTO {

	private Long id;
	private BranchResponseDTO branch;
	private String branchName;
	private String branchCode;
	private String docId;
	private String belongsTo;
	private LocalDate docDate;
	private CustomerOtherSalesResponseDTO supplier;
	private String grnNo;
	private LocalDate grnDate;
	private String isIgstAppl;
	private Boolean excisable;
	private String vendorDcNo;
	private BigDecimal exchangeRate;
	private String dealerType;
	private String purchaseorderNumber;
	private String purchaseorderType;
	private LocalDate purchaseorderDate;
	private Boolean isReverseChrg;
	private LocalDate voucherPostingDate;
	private BigDecimal dutyPerUnit;
	private Boolean modvatCopyReceived;
	private String supplierDcInvNo;
	private LocalDate supplierDcInvDate;
	private String amountInWords;
	private Boolean entryTaxApplicable;
	private String narration;
	private String paymentTerms;

	// Totals
	private BigDecimal totalFreight;
	private BigDecimal totalQty;
	private BigDecimal basicValue;
	private BigDecimal totalAmount;

	private Long orgId;
	private String financialYear;
	private String active;
	private String cancel;
	private String cancelRemarks;
	private String createdBy;
	private String updatedBy;
	private String screenCode;
	private String screenName;

	private List<PurchaseReturnDetailsResponseDTO> purchaseReturnDetailsResponseDTO;
	private List<PurchaseReturnTaxDetailsResponseDTO> purchaseReturnTaxDetailsResponseDTO;
}