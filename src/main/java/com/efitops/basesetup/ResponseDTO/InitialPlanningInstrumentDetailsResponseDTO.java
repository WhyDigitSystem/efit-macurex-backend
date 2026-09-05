package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningInstrumentDetailsResponseDTO {
	
	private MachineInstrumentResponseDTO instrumentNo;


	private String range;

}
