package com.efitops.basesetup.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningDetailsDTO {
    
	private Long id;
	
	private String parameter;
	
	private String specification;
	
	private Long uom;
	
	private String accCriteria;

	private String inspectionMethod;
	
	private int noOfInstrumentsUsed;
	
	private String remarks;
	
	private List<InitialPlanningInstrumentDetailsDTO>initialPlanningInstrumentDetailsDTO;
	

}
