package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParameterMasterResponse1DTO {
	
	    private Long id;

	    private String parameterCode;

	    private ListOfValuesDetailsResponseDTO parameterType;

	    private String parameterDescription;

}
