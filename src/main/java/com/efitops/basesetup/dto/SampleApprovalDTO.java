package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleApprovalDTO {

	private Long id;
	private String routeCardNo;
	private String partNo;
	private String partName;
	private String drgNo;
	private String operation;
	private int cycleTime;
	private String machineNo;
	private String machineName;
	private String jobOrderNo;
	private String shift;
	private LocalDate shiftDate;
	private LocalDateTime shiftTime;
	private int sampleQty;
	private String docFormatNo;

	// SUMMARY
	private String generalRemarks;
	private String operatorName;
	private String shiftInCharge;
	private String qualityName;
	private String narration;

	private String createdBy;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;

	List<SampleApprovalDetailsDTO> sampleApprovalDetailsDTO;
//	List<SampleApprovalImagesDTO> sampleApprovalImagesDTO;
}
