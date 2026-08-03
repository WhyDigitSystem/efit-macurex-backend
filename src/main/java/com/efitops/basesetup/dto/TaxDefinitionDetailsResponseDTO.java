package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionDetailsResponseDTO {

	private ListOfVlauesDetailsResponseDTO taxType;

	private ListOfVlauesDetailsResponseDTO taxName;

	private String addLess;

	private Double taxPercent;

	private String taxId;

	private String formula;

	private String postToFinance;

	private String dbCr;

	private String glAccountName;

	private String print;

	private String taxPost;
}
