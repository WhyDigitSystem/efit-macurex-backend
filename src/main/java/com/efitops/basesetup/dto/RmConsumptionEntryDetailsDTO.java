package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmConsumptionEntryDetailsDTO {
	private Long unit;
	private BigDecimal consumptionAsPerBomQty;
	private BigDecimal availableStock;
	private BigDecimal wastageQty;
	private BigDecimal scrapQty;
	private BigDecimal rate;
	private BigDecimal consumedQty;
	private Long item;
	
}