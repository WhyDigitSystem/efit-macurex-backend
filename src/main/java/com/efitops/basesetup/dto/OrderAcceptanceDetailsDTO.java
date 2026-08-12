package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceDetailsDTO {

	private Long item;

	private String customerPartNo;

	private String taxType;

	private Long taxPercentage;

	private Long unit;

	private LocalDate lastInvoiceDate;

	private BigDecimal quantity;

	private BigDecimal quantityRate;

	private BigDecimal orderRate;

	private BigDecimal discount;

//	private BigDecimal sgstRate;
//
//	private BigDecimal sgstAmount;
//
//	private BigDecimal cgstRate;
//
//	private BigDecimal cgstAmount;
//
//	private BigDecimal igstRate;
//
//	private BigDecimal igstAmount;

	private String currencyName;

}
