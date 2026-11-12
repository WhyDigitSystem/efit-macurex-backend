package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CauseMasterResponseDTO {

	private Long id;

	private DepartmentResponseDTO department;

	private ListOfValuesDetailsResponseDTO maintenanceType;

	private String causeCode;

	private String cause;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String financialYear;

	private String cancelRemarks;

}
