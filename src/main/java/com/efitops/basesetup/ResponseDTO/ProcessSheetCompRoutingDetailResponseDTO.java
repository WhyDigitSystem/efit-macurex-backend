package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSheetCompRoutingDetailResponseDTO {
	
	private Long id;

    private LocationResponseDTO location;

    private OperationMasterResponseforPSCRDTO operation;

    private String description;

    private ItemResponse1DTO outputItemCode;

    private String spec;

    private Long noOfToolsFixture;

    private Long sequence;

    private BigDecimal activityConsumCost;

    private BigDecimal cumulativeConsumCost;

    private String sourceOfVariation;

    private String productCharacteristics;

    private String processCharacteristics;

}
