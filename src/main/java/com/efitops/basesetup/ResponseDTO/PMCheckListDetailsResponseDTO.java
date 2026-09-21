package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListDetailsResponseDTO {
	
	private ListOfValuesDetailsResponseDTO category;

	private ActivityResponseDTO activity;

	private String checkingPoints;

	private String parameter;

	private String specification;

	private String generalDevObs;

	private String remediesRemarks;

	private BigDecimal noOfHrs;

	private String frequency;


}
