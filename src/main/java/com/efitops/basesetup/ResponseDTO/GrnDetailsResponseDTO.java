package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDetailsResponseDTO {

	private Long id;

	private ItemMasterDetailsResponseDTO item;

//	private UnitResponseDTO purchaseUnit;
//	private UnitResponseDTO primaryUnit;
	private UnitResponseDTO poUnit;
	private UnitResponseDTO receivedUnit;
	private UnitResponseDTO accUnit;

	private BigDecimal stock;
	private BigDecimal purchaseTolerance;
	private String inspectionable;
	private LocalDate manufacturedDate;
	private BigDecimal poRate;
	private BigDecimal poQty;
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

	// Tax fields
	private BigDecimal taxPercentage;
	private BigDecimal amount;
	private String hsnCode;
	private String taxType;
	private BigDecimal sgstRate;
	private BigDecimal sgstAmount;
	private BigDecimal cgstRate;
	private BigDecimal cgstAmount;
	private BigDecimal igstRate;
	private BigDecimal igstAmount;
	private BigDecimal apportionedCost;

	// Cost fields
	private BigDecimal insurance;
	private BigDecimal bankchrg;
	private BigDecimal lcost;
	private BigDecimal landedCostRate;
	private BigDecimal landedValue;
}