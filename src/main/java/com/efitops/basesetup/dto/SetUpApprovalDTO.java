package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalDTO {

	private Long id;

	private Long branch;

	private Long shift;

	private Long item;

	private String processSheetNo;

	private Long customer;

	private String controlPlan;

	private Long checkedBy;

	private Long approvedBy;

	private String recommendedForProduction;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<SetUpApprovalDetailsDTO> setUpApprovalDetailsDTO;
	
	private List<SetUpApprovalParametersDetailsDTO> setUpApprovalParametersDetailsDTO;
	
	

}
