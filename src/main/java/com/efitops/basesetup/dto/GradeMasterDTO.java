package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GradeMasterDTO {
	
	 private Long id;
	 private String gradeCode;
	 private String gradeDescription;
	 private String remarks;
	 private Long orgId;
	 private String createdBy;
	 private String cancelRemarks;
	 private String description;
	 private boolean active;
	 private Long branch;


}
