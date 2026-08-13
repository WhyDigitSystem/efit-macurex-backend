package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectionInvoiceDetailsResponseDTO {

	private Long id;

	private ItemMasterDetailsResponseDTO item;

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

	private BigDecimal amtInSelectedCurrency;

	private BigDecimal amountInRs;

	private BigDecimal sgstRate;

	private BigDecimal sgstAmount;

	private BigDecimal cgstRate;

	private BigDecimal cgstAmount;

	private BigDecimal igstRate;

	private BigDecimal igstAmount;

	private String hsnCode;

}
