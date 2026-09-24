package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolBreakdownResponseDTO {

	private Long id;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private ToolCategoryResponse1DTO selectMachineToolInst;

	private String machineToolIdInst;

	private String machineName;

	private String location;

	private PMCheckListResponseDTO pmCheckListNo;

	private LocalTime breakdownTime;

	private LocalTime reportedTime;

	private LocalDate reportedDate;

	private String image;

	private EmployeeDropdownResponseDTO operatorName;

	private ListOfValuesDetailsResponseDTO maintenanceType;

	private ListOfValuesDetailsResponseDTO natureOfBreakdown;

	private String natureOfProblem;

	private String estimatedTime;

	private ListOfValuesDetailsResponseDTO breakdownType;

	private String remarks;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String FinancialYear;
	
	private String cancelRemarks;
	
	private List<MachineToolBreakdownAttchmentResponseDTO> machineToolBreakdownAttchmentResponseDTO;

}
