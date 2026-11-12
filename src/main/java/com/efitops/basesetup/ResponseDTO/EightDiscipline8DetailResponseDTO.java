package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline8DetailResponseDTO {
	
	
	private Long id;

    private String teamAndIndividualRecognition;

    private LocalDate closedDate;

    private String reportedBy;

}
