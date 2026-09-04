package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferGrnDetailsResponseDTO {

	private Long id;
	private BigDecimal stock;
	private BigDecimal purchaseTolerance;
	private String inspectionable;
	private BigDecimal poRate;
	private BigDecimal poQty;
	private BigDecimal challanQty;
	private BigDecimal receivedQty;
	private BigDecimal storeStock;
	private BigDecimal pendingQty;
	private BigDecimal conversionFactor;
	private BigDecimal recQtyInPrimaryUnit;
	private BigDecimal acceptQty;
	private BigDecimal accQtyInPrimaryUnit;
	private BigDecimal rejectQty;
	private BigDecimal rejQtyInPrimaryUnit;
	private BigDecimal excessQty;
	private BigDecimal itemMaxQty;
	private BigDecimal taxPercentage;
	private BigDecimal amount;
	private BigDecimal apportionedCost;
	private BigDecimal insurance;
	private BigDecimal bankchrg;
	private BigDecimal lcost;
	private BigDecimal landedCostRate;
	private BigDecimal landedValue;
	private BigDecimal handCharge;

	// Nested DTOs
	private ItemMasterDetailsResponseStockDTO item;
//		
	private UnitResponseDTO poUnit;
	private UnitResponseDTO receivedUnit;
	private UnitResponseDTO accUnit;
}
