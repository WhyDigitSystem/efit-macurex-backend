package com.efitops.basesetup.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingApprovalDTO {

	private Long id;
	private String routeCardNo;
	private String partNo;
	private String partName;
	private String drgNo;
	private String operation;
	private int cycleTime;
	private String machineNo;
	private String machineName;
	private int sampleQty;
	private String grnClearTime;
	private String docFormatNo;

	// summary

	private String generalRemarks;
	private String operatorName;
	private String setterName;
	private String shiftInCharge;
	private String qualityName;
	private String narration;
	private String createdBy;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;

	List<SettingApprovalDetailsDTO> settingApprovalDetailsDTO;
	List<SettingApprovalDocumentsDTO> settingApprovalDocumentsDTO;

}
