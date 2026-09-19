package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline4DetailResponseDTO {
	
	private Long id;

    private String why1;

    private String why2;

    private String why3;

    private String why4;

    private String how;

    private String correctiveAction;

    private String preventiveAction;

    private String rootCauseForNonConformity;

}
