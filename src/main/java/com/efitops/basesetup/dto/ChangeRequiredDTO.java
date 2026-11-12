package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequiredDTO {
	
	private String fixtures;

	private String anyChanges;
	
	private BigDecimal estimatedCost;

	private BigDecimal leadTime;


}
