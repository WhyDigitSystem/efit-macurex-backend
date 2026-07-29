package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaseDTO {
	private Long id;

	private String defaultSupplier;

	private String alternateSupplier;

	private BigDecimal leadTime;

	private BigDecimal purchaseTolerance;

	private BigDecimal rate;

	private LocalDate date;

	private BigDecimal landedCostRate;

	private Long branchId;

	private String toolOwner;

	private String toolNo;

	private BranchResponseDTO branch;

}
