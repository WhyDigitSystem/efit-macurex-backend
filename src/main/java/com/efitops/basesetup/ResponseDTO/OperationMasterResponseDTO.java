package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterResponseDTO {

	private Long id;

	private String operationId;

	private String description;

	private String active;

	private Long orgId;

	private String createdBy;

	private String cancelRemarks;
	
	private List<OperationMasterToolDetailsResponseDTO> operationMasterToolDetailsResponseDTO;
	
	private List<OperationMasterMachineDetailsResponseDTO> operationMasterMachineDetailsResponseDTO;
	
	private List<OperationMasterConsumablesDetailsResponseDTO> operationMasterConsumablesDetailsResponseDTO;

}
