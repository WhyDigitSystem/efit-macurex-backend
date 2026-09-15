package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipDTO {

	private Long id;

	private String belongsTo;

	private Long fromLocation;
	private Long toLocation;
	private Long scrapToLocation;

	private Long fgPartNo;
	private Long sfgPartNo;
	private String sfgDescription;

	private String schOrderNo;
	private String bom;
	private LocalDate schDates;
	private String alterInputItem;
	private String itemType;
	private BigDecimal issueQty;
	private String unit;
	private BigDecimal value;
	private BigDecimal rate;

	private String createdBy;
	private Long orgId;
	private String financialYear;
	private Long branch;
	private String remarks;
	private BigDecimal totalValue;
	private boolean active;
	private String cancelRemarks;

	private List<ProductionTransferSlipDetailsDTO> productionTransferSlipDetailsDTO;
}