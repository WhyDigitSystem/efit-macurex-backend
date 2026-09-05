package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetToolFixtureDetailsResponseDTO {
	
	private Long id;

    private ListOfValuesDetailsResponseDTO usageType;

    private ToolMasterResponseDTO toolFixtureNo;

    private String toolFixtureName;

    private BigDecimal activityToolFixtureCost;

}
