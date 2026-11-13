package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RootCauseAnalysisDetailsResponseDTO {
	
	 private Long id;

	    private String why1;

	    private String why2;

	    private String why3;

	    private String why4;

	    private String why5;

	    private String how;

	    private String correctiveAction;

	    private String preventiveAction;

	    private String remarks;

}
