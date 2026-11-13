package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueDetailsDTO {

	private Long id;
	private Long item;
	private String itemDescription;
	private Long unit;
	private BigDecimal availableQty;
	private BigDecimal indReqQty;
	private BigDecimal issueQty;
	private BigDecimal rate;
	private BigDecimal totalQty;
}
