package com.efitops.basesetup.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessMasterDTO {
	private Long id;
	private String processName;
	private Long orgId;
	private String createdBy;
	private String branch;
	private String branchCode;
    private String finYear;
	private boolean active;
}
