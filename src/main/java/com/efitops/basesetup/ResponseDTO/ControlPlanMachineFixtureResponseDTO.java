package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanMachineFixtureResponseDTO {
	
	 private Long id;

	    private MachineMasterResponse1DTO machineFixtureNo;

	    private String machineFixtureName;
}
