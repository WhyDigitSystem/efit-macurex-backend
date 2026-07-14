package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderShortCloseDTO {
	private Long id;
	private String customerName;
	private String customerCode;
	private String customerPoNo;
	private String workOrderNumber;
	private String currency;
	private String productionMgr;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;

	List<WorkOrderShortCloseDetailsDTO> workOrderShortCloseDetailsDTO;
}
