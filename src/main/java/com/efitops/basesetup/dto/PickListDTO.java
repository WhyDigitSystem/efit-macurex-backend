package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListDTO {

	private Long id;

	private String customerName;

	private String routeCardNo;

	private String workOrderNo;

	private String itemIssueToProductionNo;

	private String department;

	private String location;

	private String shift;

	private String pickedBy;

	private String fgPartNo;
	
	private String status;

	private String createdBy;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String remarks;
	private String customerCode;

	List<PickListDetailsDTO> pickListDetailsDTO;
}
