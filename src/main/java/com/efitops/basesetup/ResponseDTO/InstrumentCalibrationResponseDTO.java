package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationResponseDTO {

	private Long id;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private EmployeeDropdownResponseDTO checkedBy;

	private String selectMachineInstNo;

	private MachineMasterResponse1DTO machineInstNo;

	private LocationMasterResponseDTO location;

	private ListOfValuesDetailsResponseDTO calibrationAgency;

	private String certificateNo;

	private EmployeeDropdownResponseDTO approvedBy;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<InstrumentCalibrationDetailsResponseDTO> instrumentCalibrationDetailsResponseDTO;
}
