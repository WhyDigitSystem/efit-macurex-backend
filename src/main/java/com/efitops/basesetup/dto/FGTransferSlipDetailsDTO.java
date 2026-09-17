package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FGTransferSlipDetailsDTO {

	private Long item;
	private Long unit;
	private BigDecimal bomQty;
	private BigDecimal availableStock;
	private BigDecimal wastageQty;
	private BigDecimal rate;
	private Long scrap;
	private BigDecimal scrapQty;
	private Long scrapUnit;
}
