package com.efitops.basesetup.dto;

import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPatrolInspectionDTO {

	private Long id;

	private String routeCardNo;

	private String partNo;

	private String partName;

	private String drgNo;

	private String shift;

	private String machineNo;

	private String machineName;

	private String time;

	private String jobOrderNo;

	private String documentFormatNo;

	private String narration;

	private String createdBy;

	private boolean active;

	private Long orgId;

	private String branch;

	private String branchCode;

	private String finYear;

	private List<DailyPatrolInspectionDetails1DTO> dailyPatrolInspectionDetails1DTO;

	private List<DailyPatrolInspectionFinalDTO> DailyPatrolInspectionFinalDTO;
	
	private List<DailyPatrolInspectionDocumentsDTO> dailyPatrolInspectionDocumentsDTO;


}
