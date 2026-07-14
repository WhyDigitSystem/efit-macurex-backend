package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractGrnDetailsDTO {
	private String itemCode;
	private String itemDesc;
	private String taxType;
	private String primaryUnit;
	private BigDecimal poRate; 
	private BigDecimal qty;
	private BigDecimal pendingQty;
	private BigDecimal recievedQty;
	private BigDecimal acceptQty;
	private BigDecimal rejectQty;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;
	private BigDecimal taxValue;
	private BigDecimal landedValue;

}
