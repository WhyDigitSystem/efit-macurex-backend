package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationForBreakdownDTO {

	private Long id;

	private Long branch;

	private Long department;

	private String rectificationNo;

	private String rectificationDate;

	private String breakdownNo;

	private String breakdownDate;

	private String working;

	private String problem;

	private String solution;

	private String machineNo;

	private String rectifiedTime;

	private Long authorizedBy;

	private String ReasonIfNo;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;

}
