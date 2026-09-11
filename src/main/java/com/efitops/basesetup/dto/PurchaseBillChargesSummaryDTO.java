package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillChargesSummaryDTO {

	private BigDecimal totalFreight;
	private BigDecimal totalQty;
	private BigDecimal basicValue;
	private BigDecimal totalAmount;
	private String amountInWords;
	private Boolean entryTaxApplicable;
	private String narration;
	private String paymentTerms;
}
