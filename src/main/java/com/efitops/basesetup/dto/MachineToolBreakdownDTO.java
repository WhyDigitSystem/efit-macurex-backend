package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolBreakdownDTO {

	private Long id;

	private Long branch;

	private Long department;

	private Long selectMachineToolInst;

	private String machineToolIdInst;

	private String machineName;

	private String location;

	private Long pmCheckListNo;

	private LocalTime breakdownTime;

	private LocalTime reportedTime;

	private LocalDate reportedDate;

	private String image;

	private Long operatorName;

	private Long maintenanceType;

	private Long natureOfBreakdown;

	private String natureOfProblem;

	private String estimatedTime;

	private Long breakdownType;

	private String remarks;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String FinancialYear;
	
	private String CancelRemarks;

}
