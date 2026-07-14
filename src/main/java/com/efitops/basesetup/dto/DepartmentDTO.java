package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
	private Long id;
	private String departmentName;
	private String departmentCode;
	private boolean active;
	private Long orgId;
	private String branch;
	private String branchCode;
    private String finYear;
	private String createdBy;
}