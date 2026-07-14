package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanDTO {
	private Long id;
	private String routeCardNo;
	private String woSoNo;
	private LocalDate woSoDate;
	private String customerName;
	private String part;
	private String partDesc;
	private String productionQty;
	private LocalDate productionStartDate;
	private LocalDate productionEndDate;
	private String rawMaterial;
	private String rawMaterialDesc;
	private String narration;
	
	private Long orgId;
//	private String branch;
//	private String branchCode;
//	private String finYear;
	private String createdBy;

	
	List<ProductionPlanDetailsDTO> productionPlanDetailsDTO;
}
