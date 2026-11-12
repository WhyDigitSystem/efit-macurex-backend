package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMasterDTO {

	private Long id;
	private String screenCode;
	private String screenName;
	private String description;
	private String docCode;
	private String createdBy;
	private Long orgId;
 	 
 	 

}
