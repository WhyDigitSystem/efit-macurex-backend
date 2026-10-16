package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingDTO {

	private Long id;

	private Long branch;

	private String fgSfgItemType;

	private Long fgSfgItemCode;

	private String itemDescription;

	private Long bom;

	private boolean active;

	private BigDecimal totalMcValue;

	private BigDecimal totalLabourValue;

	private BigDecimal totalToolFixtureValue;

	private BigDecimal totalConsumablesValue;

	private BigDecimal totalOperationValue;

	private Long preparedBy;

	private Long orgId;

	private String createdBy;

	private String updatedBy;

	private boolean cancel;

	private String cancelRemarks;

	private String costRate;

	private List<ProcessSheetCompRoutingDetailDTO> processSheetCompRoutingDetailDTO;

	private List<ProcessSheetCompRoutingMachineDTO> processSheetCompRoutingMachineDTO;

	private List<ProcessSheetToolFixtureDetailsDTO> processSheetToolFixtureDetailsDTO;

}
