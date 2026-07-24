package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransportMasterDTO {
	
	private Long id;
	private Long orgId;
	private String transportName;
	private String address;
	private Boolean active;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String cancelRemarks;

}
