package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherSalesInvoiceDetailsDTO {

	private Long item;

	private String taxType;

	private BigDecimal taxPercentage;

	private String tariffNo;

	private String stock;

	private String salesOrderContractNo;

	private BigDecimal qty;

	private BigDecimal noOfPackages;

	private String packageType;

	private BigDecimal orderRate;
	
	private BigDecimal rateInSelectedCurrency;
	
	private String hsnCode;

}
