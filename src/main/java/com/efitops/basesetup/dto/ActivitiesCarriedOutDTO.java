package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutDTO {

	private Long id;

	private Long branch;

	private Long department;

	private Long checkedBy;

	private Long selectMachineToolInst;

	private String machineToolInstNo;

	private String Location;

	private Long pmCheckListNo;

	private Long maintenanceType;

	private Long fromLocation;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<ActivitiesCarriedOutDetailsDTO> activitiesCarriedOutDetailsDTO;
	
	private List<ActivitiesCarriedOutComponentDetailsDTO> activitiesCarriedOutComponentDetailsDTO;

}
