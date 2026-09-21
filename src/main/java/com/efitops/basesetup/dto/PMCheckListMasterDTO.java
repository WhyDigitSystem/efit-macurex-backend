package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListMasterDTO {
	private Long id;

	private Long branch;

	private Long department;

	private Long pmCheckListFor;

	private String pmCheckListNo;

	private Long toolCategory;

	private Long preparedBy;

	private Long approvedBy;

	private Boolean active;

	private Long orgId;

	private String createdBy;

	private String financialYear;

	private String cancelRemarks;
	
	private List<PMCheckListDetailsDTO> pmCheckListDetailsDTO;
}
