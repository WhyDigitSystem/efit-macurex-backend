package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionScheduleOrderDetailsDTO {

	private Long id;

	private Long item;
	private BigDecimal bomQty;
	private BigDecimal qtyRequired;
	private Long unit;
	private Long scrapUnit;
	private BigDecimal scrapQty;
}