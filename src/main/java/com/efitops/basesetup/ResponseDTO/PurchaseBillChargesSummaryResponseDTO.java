package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBillChargesSummaryResponseDTO {
	private Long id;
	private BigDecimal totalFreight;
	private BigDecimal totalQty;
	private BigDecimal basicValue;
	private BigDecimal totalAmount;
	private String amountInWords;
	private Boolean entryTaxApplicable;
	private String narration;
	private String paymentTerms;
}