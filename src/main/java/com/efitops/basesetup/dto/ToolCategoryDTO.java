package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ToolCategoryDTO {
	
	private Long id;
	
	private String apllicableFor;
	
	private boolean active;
	 
	private Long orgId;
		
	private String createdBy;
		
	private String cancelRemarks;
	
	private List<ToolCategoryDetailDTO> toolCategoryDetailDTO;
	   

}
