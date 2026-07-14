package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private BigDecimal holdQty;
	private BigDecimal AvgQty;
	private BigDecimal reqQty;
	private BigDecimal issueQty;
	private BigDecimal pendingQty;
	private BigDecimal pickQty;
}
