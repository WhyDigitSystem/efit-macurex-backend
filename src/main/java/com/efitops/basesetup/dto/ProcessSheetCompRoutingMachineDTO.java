package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingMachineDTO {
	

    private Long id;

    private Long usage;

    private Long machineNo;

    private String machineName;

    private BigDecimal setupTimeMinutes;

    private BigDecimal outputPerHour;

    private BigDecimal machineHourRate;

    private BigDecimal activityMcCost;

    private BigDecimal labourHourMinutes;

    private BigDecimal labourHourRate;

    private BigDecimal activityLabourCost;

    private BigDecimal total;

}
