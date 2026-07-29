package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ItemPurchaseResponseDTO {

	private Long id;
	private String defaultSupplier;
	private String alternateSupplier;
	private BigDecimal leadTime;
	private BigDecimal purchaseTolerance;
	private BigDecimal rate;
	private LocalDate date;
	private BigDecimal landedCostRate;

	private BranchResponseDTO branch;

	private String toolOwner;
	private String toolNo;
}
