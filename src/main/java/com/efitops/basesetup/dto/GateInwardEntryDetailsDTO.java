package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateInwardEntryDetailsDTO {


	private String itemName;

	private String itemDesc;

	private String uom;

	private BigDecimal poQty;

	private BigDecimal invoiceQty;

	private BigDecimal inwardQty;
	
	private BigDecimal poBalanceQty;

//	private int balanceQty;
//
//	private int excessQty;
}
