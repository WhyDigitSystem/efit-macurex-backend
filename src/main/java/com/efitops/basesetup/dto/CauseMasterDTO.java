package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CauseMasterDTO {

	private Long id;

	private Long department;

	private Long maintenanceType;

	private String causeCode;

	private String cause;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String financialYear;


	private String cancelRemarks;

}
