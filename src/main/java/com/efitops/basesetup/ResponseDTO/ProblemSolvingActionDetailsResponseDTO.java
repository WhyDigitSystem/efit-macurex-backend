package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingActionDetailsResponseDTO {
	
	private String action;

	private String description;

	private EmployeeDropdownResponseDTO responsible;

	private LocalDate implDate;

}
