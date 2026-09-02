package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturnDetailsResponseDTO {

	private Long id;
	private ItemMasterDetailsResponseInwardDTO item;
	private String hsnSacCode;
	private String taxType;
	private BigDecimal taxPercentage;
	private String tariffNo;
	private String exciseToPost;

	private BigDecimal challanQty;
	private UnitMasterResponseDTO unit;

	private BigDecimal grnReceivedQty;
	private BigDecimal acceptedQty;
	private BigDecimal rejectedQty;
	private BigDecimal shortageQty;

	// Rate Details
	private BigDecimal poRate;
	private BigDecimal rateInInr;
	private BigDecimal rateInSelectedCurrency;
	private BigDecimal apportionedCost;
	private BigDecimal landedCostRate;

	private BigDecimal amount;
	private BigDecimal amountInSelectedCurrency;
	private BigDecimal additionalDuty;
	private BigDecimal amountInInr;

	private BigDecimal sgstRate;
	private BigDecimal sgstAmount;
	private BigDecimal cgstRate;
	private BigDecimal cgstAmount;
	private BigDecimal igstRate;
	private BigDecimal igstAmount;
}