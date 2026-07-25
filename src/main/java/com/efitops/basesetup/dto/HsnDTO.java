package com.efitops.basesetup.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 

public class HsnDTO {
	
	 private Long id;
	 private Long orgId;
	 private String createdBy;
	 private String cancelRemarks;
	 private Long listofvalues;
	 private String hsn;
	 private String description;
	 private boolean active;
	 private String branch;
	private String branchCode;
		

}
