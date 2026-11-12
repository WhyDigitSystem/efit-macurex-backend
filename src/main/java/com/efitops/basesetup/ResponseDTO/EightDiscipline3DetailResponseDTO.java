package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EightDiscipline3DetailResponseDTO {
	
	

    private Long id;

    private String interimContainmentAction;

    private String responsiblePersonName;

    private LocalDate dateImplemented;

    private String responsibility;

}
