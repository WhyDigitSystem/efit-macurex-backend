package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDTO {
	private Long id;
	private String customerName;
	private String customerPoNo;
	private String quotationNo;
	private Long quotationId;
	private String customerCode;
	private String currency;
	private LocalDate customerDueDate;
	private LocalDate dueDate;
	private String productionMgr;
	private String customerSpecialRequirement;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;
	List<WorkOrderDetailsDTO> workOrderDetailsDTO;
	List<WorkOrderTermsDTO> workOrderTermsDTO;

}
