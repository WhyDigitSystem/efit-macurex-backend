package com.efitops.basesetup.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanParameterDTO {
	
	private Long id;
	
	private Long parameter;
	
	private String parameterType;
	
	private String tol;
	 
	 

}
