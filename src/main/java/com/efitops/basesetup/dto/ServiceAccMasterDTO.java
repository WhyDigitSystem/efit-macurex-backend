package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceAccMasterDTO {
	private Long id;
	private String serviceName;
	private String serviceDescription;
	private String hsncode;
	private Long orgId;
	private boolean active;
	private String createdBy;;
	private Long branchId;
	private String cancelRemarks;
	


}
