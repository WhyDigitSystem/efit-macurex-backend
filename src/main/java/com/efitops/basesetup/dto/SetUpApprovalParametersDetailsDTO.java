package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalParametersDetailsDTO {
	
	private String parameters;
	
	private String parameterType;
	
	private String tol;

}
