package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterTechnicalInfoDetailsDTO {


    private BigDecimal toolWeight;

    private Long unit;

    private String toolFixtureSize;

    private String lifeOfTool;

    private Long lifeType;

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

