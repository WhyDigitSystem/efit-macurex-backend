package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline8DetailDTO {
	
	
	    private Long id;

	    private String teamAndIndividualRecognition;

	    private LocalDate closedDate;

	    private String reportedBy;

}
