package com.efitops.basesetup.ResponseDTO;


import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;

	private ShiftResponseDTO shift;

	private ItemResponse1DTO item;

	private String processSheetNo;

	private CustomerResponse1DTO customer;

	private String controlPlan;

	private EmployeeMasterResponseDetailsDTO checkedBy;

	private EmployeeMasterResponseDetailsDTO approvedBy;

	private String recommendedForProduction;

	private Long orgId;

	private String financialYear;

	private String active;

	private boolean cancel = false;

	private String cancelRemarks;

	private String createdBy;
	
	private List<SetUpApprovalDetailsResponseDTO> setUpApprovalDetailsResponseDTO;
	
	private List<SetUpApprovalParametersDetailsResponeDTO> setUpApprovalParametersDetailsResponeDTO;

}
