package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseCashDetailsResponseDTO {

	private Long id;
	private String itemCode;
	private String itemDescription;
	private String hsnCode;
	private String taxType;
	private BigDecimal taxPercentage;

	private UnitResponseDTO unit;

	private BigDecimal dcQty;
	private BigDecimal receivedQty;
	private BigDecimal rate;
	private BigDecimal amount;

	private BigDecimal cgstRate;
	private BigDecimal cgstAmount;
	private BigDecimal sgstRate;
	private BigDecimal sgstAmount;
	private BigDecimal igstRate;
	private BigDecimal igstAmount;
}