package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class EightDiscipline5DetailDTO {
	
	
	private Long id;

    private String permanentCorrectiveActions;

    private String responsiblePersonName;

    private LocalDate dateImplemented;

}
