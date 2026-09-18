package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessChangesDTO {
	private String processChange;

	private String layOut;

	private String actions;

	private BigDecimal estimatedCost;

	private BigDecimal leadTime;

}
