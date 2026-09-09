package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationResponseDTO {

	private Long id;

	private Long branch;

	private Long department;

	private EmployeeDropdownResponseDTO checkedBy;

	private String selectMachineInstNo;

	private MachineMasterResponse1DTO machineInstNo;

	private LocationMasterResponseDTO location;

	private String calibrationAgency;

	private String certificateNo;

	private EmployeeDropdownResponseDTO approvedBy;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
}
