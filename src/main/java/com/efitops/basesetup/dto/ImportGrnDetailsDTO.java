package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportGrnDetailsDTO {

	private Long item;

	private Long uom;

	private Long poUnit;

	private String stock;

	private String inspectionable;

	private BigDecimal poQty;

	private BigDecimal balancePoQty;

	private BigDecimal challanQty;

	private BigDecimal receivedQty;

	private BigDecimal acptQty;

	private BigDecimal fobRateFC;

	private BigDecimal freight;

	private BigDecimal bcdValueINR;

	private BigDecimal exciseCvdIgst;

	private BigDecimal addDuty;

	private BigDecimal clearingCharge;

	private BigDecimal bankCharge;

	private BigDecimal packingCharge;

	private BigDecimal surcharge;

	private BigDecimal specialCost;

	private BigDecimal handlingCharge;

	private BigDecimal freightInd;

}