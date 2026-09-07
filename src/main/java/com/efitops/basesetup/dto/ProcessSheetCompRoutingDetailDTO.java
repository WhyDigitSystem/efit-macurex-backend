package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingDetailDTO {
	
	
	    private Long id;

	    private Long location;

	    private Long operation;

	    private String description;

	    private Long outputItemCode;

	    private String spec;

	    private Long noOfToolsFixture;

	    private Long sequence;

	    private BigDecimal activityConsumCost;

	    private BigDecimal cumulativeConsumCost;

	    private String sourceOfVariation;

	    private String productCharacteristics;

	    private String processCharacteristics;
	    
	   
		 

}
