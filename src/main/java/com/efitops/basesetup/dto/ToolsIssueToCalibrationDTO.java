package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolsIssueToCalibrationDTO {

	private Long id;
	private String issuePartyName;
	private String issuePartyAddress;
	private String branch;
	private String branchCode;
	private String finYear;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private String issueCreatedBy;
	private String remarks;
	private String narration;

	List<ToolsIssueToCalibrationDetailsDTO> toolsIssueToCalibrationDetailsDTO;

}
