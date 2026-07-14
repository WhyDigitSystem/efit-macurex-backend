package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FgStockUpdateDetailsDTO {

	private String part;
	private String partDesc;
	private BigDecimal qty;
	private BigDecimal actualQty;
	private String unit;
	private BigDecimal rate;
	private BigDecimal availableQty;
}
