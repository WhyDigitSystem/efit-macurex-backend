package com.efitops.basesetup.dto;

import com.efitops.basesetup.entity.MachineMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanMachineFixtureDTO {
	
	
	private Long id;
	
	private Long machineFixture;
	
	 private String machineFixtureName;
	 

}
