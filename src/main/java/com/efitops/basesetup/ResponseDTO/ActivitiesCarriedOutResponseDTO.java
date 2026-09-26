package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private EmployeeMasterResponseDetailsDTO checkedBy;

	private ToolCategoryResponse1DTO selectMachineToolInst;

	private String machineToolInstNo;

	private String Location;

	private PMCheckListResponseDTO pmCheckListNo;

	private ListOfValuesDetailsResponseDTO maintenanceType;

	private LocationMasterResponseDTO fromLocation;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<ActivitiesCarriedOutDetailsResponseDTO> activitiesCarriedOutDetailsResponseDTO;
	
	private List<ActivitiesCarriedOutComponentDetailsResponseDTO> activitiesCarriedOutComponentDetailsResponseDTO;


}
