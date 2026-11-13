package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningDetailsResponseDTO {
	
	private Long id;

	private String parameter;

	private String specification;

	private UnitResponseDTO uom;

	private String accCriteria;

	private String inspectionMethod;

	private int noOfInstrumentsUsed;

	private String remarks;
	
	private List<InitialPlanningInstrumentDetailsResponseDTO>initialPlanningInstrumentDetailsResponseDTO;
	

	

}
