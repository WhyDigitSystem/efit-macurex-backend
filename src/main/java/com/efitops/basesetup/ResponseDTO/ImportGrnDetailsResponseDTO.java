package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportGrnDetailsResponseDTO {

	private Long id;

	private ItemMasterDetailsResponseDTO item;

	private UnitResponseDTO uom;

	private UnitResponseDTO poUnit;

	private String stock;

	private String inspectionable;

	private BigDecimal poQty;

	private BigDecimal balancePoQty;

	private BigDecimal challanQty;

	private BigDecimal receivedQty;

	private BigDecimal shortQty;

	private BigDecimal acptQty;

	private BigDecimal rejQty;

	private BigDecimal fobRateFC;

	private BigDecimal fobValueFC;

	private BigDecimal fobValueINR;

	private BigDecimal freight;

	private BigDecimal freightInd;

	private BigDecimal bcdValueINR;

	private BigDecimal cessAt10;

	private BigDecimal exciseCvdIgst;

	private BigDecimal addDuty;

	private BigDecimal clearingCharge;

	private BigDecimal bankCharge;

	private BigDecimal packingCharge;

	private BigDecimal surcharge;

	private BigDecimal specialCost;

	private BigDecimal handlingCharge;

	private BigDecimal totalValueFC;

	private BigDecimal totalValueINR;

	private BigDecimal landingValue;

	private BigDecimal landingCostINR;

}