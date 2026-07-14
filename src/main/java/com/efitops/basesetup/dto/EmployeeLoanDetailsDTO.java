package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoanDetailsDTO {

	private String finYear;
	private String openingBalance;
	private BigDecimal january;
	private BigDecimal february;
	private BigDecimal march;
	private BigDecimal april;
	private BigDecimal may;
	private BigDecimal june;
	private BigDecimal july;
	
	private BigDecimal august;
	private BigDecimal september;
	private BigDecimal october;
	private BigDecimal november;
	private BigDecimal december;
}
