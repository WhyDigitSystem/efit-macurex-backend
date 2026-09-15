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
public class FgTransferSlipDTO {

	private Long id;
	private String belongsTo;

	private Long fromLocation;
	private Long toLocation;
	private Long scrapLocation;

	private String transferNo;
	private LocalDate transferDate;

	private Long fgItem;

	private String bom;
	private String scheduleNo;
	private LocalDate scheduleDate;
	private BigDecimal scheduledQty;
	private BigDecimal qtyForInspection;

	private Long customer;

	private BigDecimal rate;
	private String remarks;

	private String createdBy;
	private Long orgId;
	private String financialYear;
	private Long branch;
	private boolean active;
	private String cancelRemarks;

	private List<FGTransferSlipDetailsDTO> fgTransferSlipDetailsDTO;
}
