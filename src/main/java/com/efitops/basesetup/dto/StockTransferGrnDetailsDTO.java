package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferGrnDetailsDTO {

	private Long item;

	private Long primaryUnit;

	private BigDecimal stock;

	private BigDecimal purchaseTolerance;

	private String inspectionable;

	private BigDecimal poRate;

	private BigDecimal poQty;

	private Long poUnit;

	private BigDecimal challanQty;

	private BigDecimal receivedQty;

	private BigDecimal storeStock;

	private BigDecimal pendingQty;

	private Long receivedUnit;

	private BigDecimal conversionFactor;

	private BigDecimal recQtyInPrimaryUnit;

	private BigDecimal acceptQty;

	private BigDecimal accQtyInPrimaryUnit;

	private Long accUnit;

	private BigDecimal rejectQty;

	private BigDecimal rejQtyInPrimaryUnit;

	private BigDecimal excessQty;

	private BigDecimal itemMaxQty;

	private BigDecimal amount;

	private BigDecimal apportionedCost;

	private BigDecimal insurance;

	private BigDecimal bankchrg;

	private BigDecimal lcost;

	private BigDecimal landedCostRate;

	private BigDecimal landedValue;

	private BigDecimal handCharge;
}