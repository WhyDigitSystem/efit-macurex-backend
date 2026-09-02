package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturnDetailsDTO {

	private Long unit;
	private Long item;
	private String itemCode;
	private String itemDescription;
	private String hsnSacCode;
	private String taxType;
	private BigDecimal taxPercentage;
	private String tariffNo;
	private String exciseToPost;
	private BigDecimal challanQty;
	private BigDecimal grnReceivedQty;
	private BigDecimal acceptedQty;
	private BigDecimal rejectedQty;
	private BigDecimal poRate;
	private BigDecimal rateInInr;
	private BigDecimal apportionedCost;
	private BigDecimal landedCostRate;
	private BigDecimal additionalDuty;
	private BigDecimal rateInSelectedCurrency;
}