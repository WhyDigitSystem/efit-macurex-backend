package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionEntryDTO {
	private Long id;
	private String belongsTo;
	private String shiftTimeFrom;
	private String shiftTimeTo;
	private String shift;

	private Long fgItem;
	private Long location;
	private BigDecimal productionQty;
	private String schOrderNo;
	private Long preparedBy;
	private String processSheetNo;
	private Long approvedBy;
	private Long bom;

	private String narration;

	// Audit & Context
	private String createdBy;
	private Long orgId;
	private String financialYear;
	private Long branch;
	private boolean active;
	private String cancelRemarks;

	// Details Lists
	private List<ProductionEntryDetailsDTO> productionEntryDetailsDTO;
	private List<ToolDetailsDTO> toolDetailsDTO;
	private List<StoppageReasonDTO> stoppageReasonDTO;
	private List<ReworkReasonDTO> reworkReasonDTO;
	private List<ScrapDetailsDTO> scrapDetailsDTO;
}
