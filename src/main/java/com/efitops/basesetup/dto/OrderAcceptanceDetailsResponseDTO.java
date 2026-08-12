package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.ResponseDTO.GSTRateResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceDetailsResponseDTO {

	private Long id;

	private ItemMasterResponseTaxDTO items;

	private UnitResponseDTO unit;

	private GSTRateResponseDTO taxPercentage;

	private LocalDate lastInvoiceDate;

	private BigDecimal quantity;

	private BigDecimal quantityRate;

	private BigDecimal orderRate;

	private BigDecimal discount;

	private BigDecimal amount;

	private BigDecimal sgstRate;

	private BigDecimal sgstAmount;

	private BigDecimal cgstRate;

	private BigDecimal cgstAmount;

	private BigDecimal igstRate;

	private BigDecimal igstAmount;

	private String currencyName;

	private String taxType;;

}
