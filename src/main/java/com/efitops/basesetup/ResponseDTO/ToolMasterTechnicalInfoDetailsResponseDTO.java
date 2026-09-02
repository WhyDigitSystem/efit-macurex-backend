package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterTechnicalInfoDetailsResponseDTO {
	private Long id;

	private BigDecimal toolWeight;

	private UnitMasterResponseDTO unit;

	private String toolFixtureSize;

	private String lifeOfTool;

	private ListOfValuesDetailsResponseDTO lifeType;

	private BigDecimal reconditionFreq;

	private BigDecimal setUpTimeInMinutes;

	private BigDecimal completedLifeCycle;

	private String toolMadeOf;

	private String technicalSpecification;

	private BigDecimal noOfStokesCompleted;

	private BigDecimal strokesCompletedAfterReconditioning;

	private LocalDate reconditionedDate;

	private BigDecimal toolFixtureCost;

	private BigDecimal toolFixtureAmortizedRecovered;

}
