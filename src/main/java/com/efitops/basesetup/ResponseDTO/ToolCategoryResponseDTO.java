package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import com.efitops.basesetup.dto.ToolCategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ToolCategoryResponseDTO {
	
    private Long id;
	
	private String apllicableFor;
	
	private boolean active;
	 
	private Long orgId;
		
	private String createdBy;
		
	private String cancelRemarks;
	
	private List<ToolCategoryDetailResponseDTO> toolCategoryDetailResponseDTO;
}
