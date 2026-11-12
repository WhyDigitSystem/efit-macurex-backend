package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSBankDTO {
	private Long id;
	private String beneficiary;
	private String bank;
	private String acno;
	private String branch;
	private String ifscCode;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private boolean active;


}
