package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipDetailsDTO {

	private Long item;
	private BigDecimal stock;
	private BigDecimal bomQty;
	private BigDecimal rate;
	private Long primaryUnit;
	private Long scrap;
	private BigDecimal scrapQty;
}
