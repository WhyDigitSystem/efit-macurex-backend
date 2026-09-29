package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionEntryDetailsDTO {
    private String operationNo;
    private Long machine;
    private BigDecimal machineHourRate;
    private BigDecimal labourHourRate;
    private String operationName;
    private BigDecimal frTimeHrs;
    private BigDecimal frTimeMins;
    private BigDecimal toTimeHrs;
    private BigDecimal toTimeMins;
    private BigDecimal lunchTimeMins;
  
    private BigDecimal stoppageTimeMins;
    private BigDecimal qtyProduced;
    private BigDecimal qtyPassed;
    private Long reason;
    private BigDecimal qtyRework;
    private Integer noOfTools;
    private BigDecimal qtyScrap;
    private Long operationBy;
    private String remarks;
    private BigDecimal stdRunTimePcsInSec;
    private BigDecimal stdLabourCost;
    private BigDecimal stdMcCost;
    private BigDecimal runningActCostLabour;
    private BigDecimal runningActCostMc;
    private BigDecimal stdToolCost;
    private BigDecimal runningActCostTool;
    private BigDecimal stdConsumCost;
    private BigDecimal runningActCostConsum;
	
}
