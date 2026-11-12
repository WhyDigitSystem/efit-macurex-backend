package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillChargesSummaryResponseDTO {
	private Long id;
	private BigDecimal totFobValueFc;
	private BigDecimal totFobValueInr;
	private BigDecimal netAmount;
	private BigDecimal totFriInsFc;
	private BigDecimal totDutyInr;
	private Boolean postVoucher;
	private BigDecimal totalValueFc;
	private BigDecimal totFreInsInr;
	private BigDecimal totLandCost;
	private String amountInWords;
	private String narration;
}