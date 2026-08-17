package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanDetailsDTO {
	private Long item;
	private String taxType;
	private String taxPercentage;
    private String hsnCode;
	private Long unit;
	private String stock;
	private BigDecimal quantity;
	private BigDecimal rate;
//	private BigDecimal totalAssessableValue;
	private BigDecimal sgstRate;
//	private BigDecimal sgstAmount;
	private BigDecimal cgstRate;
//	private BigDecimal cgstAmount;
	private BigDecimal igstRate;
//	private BigDecimal igstAmount;
	 

	

}
