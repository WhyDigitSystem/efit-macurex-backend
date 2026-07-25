package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUnitsDTO {
	
	private Long id;

	private BigDecimal purchaseUnit;

	private BigDecimal sellingUnit;

	private BigDecimal pricingUnit;

	private BigDecimal secondaryUnit;

}
