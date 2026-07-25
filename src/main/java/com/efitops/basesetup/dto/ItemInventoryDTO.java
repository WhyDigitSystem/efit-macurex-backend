package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventoryDTO {

	private Long id;
	private String manufacured;

	private String defaultLocation;

	private String alternateLocation;

	private BigDecimal leadTime;

	private String reorderLevel;

	private String rackNo;

	private String rowNo;

	private String position;

	private BigDecimal minimumOrderQty;

	private BigDecimal maximumOrderQty;

	private String binSize;

	private BigDecimal binQty;
}
