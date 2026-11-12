package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionTestingDTO {

	private String newGauge;

	private BigDecimal estimatedCost;

	private BigDecimal leadTime;

}
