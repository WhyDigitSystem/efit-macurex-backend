package com.efitops.basesetup.dto;




import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentCalibrationDTO {

	private Long id;

	private Long branch;

	private Long department;

	private Long checkedBy;

	private String selectMachineInstNo;

	private Long machineInstNo;

	private Long location;

	private Long calibrationAgency;

	private String certificateNo;

	private Long approvedBy;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<InstrumentCalibrationDetailsDTO> instrumentCalibrationDetailsDTO;

}
