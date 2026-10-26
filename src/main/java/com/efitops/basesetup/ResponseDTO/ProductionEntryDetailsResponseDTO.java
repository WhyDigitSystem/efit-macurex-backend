package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductionEntryDetailsResponseDTO {
    private Long id;
    private String operationNo;
    private MachineResponseDTO machine;
    private BigDecimal machineHourRate;
    private BigDecimal labourHourRate;
    private String operationName;
    private BigDecimal frTimeHrs;
    private BigDecimal frTimeMins;
    private BigDecimal toTimeHrs;
    private BigDecimal totTimeMins;
    private BigDecimal stoppageTimeMins;
    private BigDecimal productiveHrsMins;
    private BigDecimal qtyProduced;
    private BigDecimal qtyPassed;
    private BigDecimal qtyRejected;
    private ReasonResponseDTO reason;
    private BigDecimal qtyRework;
    private Integer noOfTools;
    private BigDecimal qtyScrap;
    private EmployeeMasterResponseDetailsDTO operationBy;
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
	private BigDecimal toTimeMins;
	private BigDecimal lunchTimeMins;
}