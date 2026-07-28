package com.efitops.basesetup.dto;

import com.efitops.basesetup.entity.ListOfValuesVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionDetailsDTO {
	
	private Long taxType;
	private Long taxName;
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
