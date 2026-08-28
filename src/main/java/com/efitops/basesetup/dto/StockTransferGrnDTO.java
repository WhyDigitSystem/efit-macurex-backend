package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferGrnDTO {

	private Long id;
	private String belongsTo;
	private Long location;
	private Long supplierCode;
	private String isIgstApplicable;
	private String isReverseCharge;
	private String gatePassNo;
	private String poNo;
	private String dealerType;
	private String scheduleNo;
	private LocalDate scheduleDate;
	private LocalDate scheduleStartDate;
	private LocalDate scheduleEndDate;
	private Long currency;
	private BigDecimal exchangeRate;
	private BigDecimal grossAmount;
	private String modvatCopyReceived;
	private BigDecimal totalQtyInKg;
	private String partyDcNo;
	private BigDecimal discount;
	private String supplierDcDate;

	private String createdBy;
	private boolean active ;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private Long branch;

	private BigDecimal netAmount;
	private BigDecimal totalAmountTax;
	private BigDecimal basicAmount;
	private LocalDate invoiceSentOn;
	private String remarks;

	private List<StockTransferGrnDetailsDTO> stockTransferGrnDetailsDTO;
}