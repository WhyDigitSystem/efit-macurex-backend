package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterDTO {
	
	private Long id;
	
	private String operationId;
	
	private String description;
	
    private boolean active;
    
    private Long orgId;

	private String createdBy;
	
	private String cancelRemarks;
	
	private List<OperationMasterToolDetailsDTO> operationMasterToolDetailsDTO;
	
	private List<OperationMasterMachineDetailsDTO> operationMasterMachineDetailsDTO;
	
	private List<OperationMasterConsumableDetailsDTO> operationMasterConsumableDetailsDTO;
	
	

}
