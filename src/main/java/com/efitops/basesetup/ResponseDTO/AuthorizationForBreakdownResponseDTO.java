package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationForBreakdownResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private String rectificationNo;

	private String rectificationDate;

	private String breakdownNo;

	private String breakdownDate;

	private String working;

	private String problem;

	private String solution;

	private String machineNo;

	private String rectifiedTime;

	private EmployeeResponseDTO authorizedBy;

	private String ReasonIfNo;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;

}
