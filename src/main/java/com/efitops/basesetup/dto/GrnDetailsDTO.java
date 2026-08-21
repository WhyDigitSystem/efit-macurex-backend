package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDetailsDTO {

	private Long id;

	private Long item;

	private Long purchaseUnit;

	private Long primaryUnit;

	private BigDecimal stock;

	private BigDecimal purchaseTolerance;

	private String inspectionable;

	private LocalDate manufacturedDate;

	private BigDecimal poRate;

	private BigDecimal poQty;

	private Long poUnit;

	private BigDecimal receivedQty;

	private BigDecimal storeStock;

	private Long receivedUnit;

	private BigDecimal acceptQty;

	private Long accUnit;

	private BigDecimal excessQty;

	private BigDecimal itemMaxQty;

	private BigDecimal taxPercentage;

	private BigDecimal amount;

	private String hsnCode;

	private String taxType;

	private BigDecimal apportionedCost;

	private BigDecimal insurance;

	private BigDecimal bankchrg;

	private BigDecimal lcost;

	private BigDecimal landedCostRate;

	private BigDecimal handCharge;

}