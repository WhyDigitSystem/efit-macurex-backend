package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingMachineResponseDTO {
	
	private Long id;

    private ListOfValuesDetailsResponseDTO usage;

    private MachineMasterResponse1DTO machineNo;

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
