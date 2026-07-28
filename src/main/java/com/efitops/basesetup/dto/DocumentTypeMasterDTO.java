package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DocumentTypeMasterDTO {

	 private Long id;
	 private String code;
	 private String name;
	 private String des;
	 private String docCode;
	 private Long orgId;
	 private String financialYear;
	 private String createdBy;
	 private String cancelRemarks;
	 private String description;
	 private boolean active;
 	 private Long branch;
 	 
 	 

}
