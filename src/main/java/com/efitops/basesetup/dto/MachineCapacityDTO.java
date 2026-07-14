package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineCapacityDTO {
	
	private Long id;

    private String itemId;

    private String itemDescription;

    private String cycleTime;

    private Long prodQtyHr;

    private String operationName;

    private String remarks;
	
	
}

