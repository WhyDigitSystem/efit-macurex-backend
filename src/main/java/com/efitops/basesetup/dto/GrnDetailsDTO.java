package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDetailsDTO {

	private String itemCode;
	private String itemDesc;
	private String hsnSacCode;
	private String taxType;
	private String primaryUnit;
	private BigDecimal stock;
	private String inspectionable;
	private BigDecimal poRate;
	private BigDecimal orderQty;
	private BigDecimal challanQty;
	private BigDecimal recievedQty;
	private BigDecimal acceptQty;
	private BigDecimal rejectQty;
	private BigDecimal excessQty;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;
	private String status;

}
