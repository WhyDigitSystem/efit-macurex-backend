package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetToolFixtureDetailsDTO {
	
	    private Long id;

	    private Long usageType;

	    private Long toolFixtureNo;

	    private String toolFixtureName;

	    private BigDecimal activityToolFixtureCost;

}
