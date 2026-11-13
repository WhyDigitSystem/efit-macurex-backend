package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceDetailsResponseDTO {

	private Long id;

	private ItemMasterDetailsResponseDTO item;

	private String taxType;

	private BigDecimal taxPercentage;
	

	private BigDecimal sgstRate;

	private BigDecimal sgstAmount;

	private BigDecimal cgstRate;

	private BigDecimal cgstAmount;

	private BigDecimal igstRate;

	private BigDecimal igstAmount;

	private String hsnCode;
	

	private BigDecimal despatchQty;	

	private BigDecimal amount;


	private BigDecimal orderRate;



}
