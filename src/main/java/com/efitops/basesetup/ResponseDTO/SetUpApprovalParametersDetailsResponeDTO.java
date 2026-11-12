package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalParametersDetailsResponeDTO {
	
	private String parameters;
	
	private String parameterType;
	
	private String tol;

}
