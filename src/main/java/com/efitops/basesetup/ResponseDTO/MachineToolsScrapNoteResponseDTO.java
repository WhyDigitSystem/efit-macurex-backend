package com.efitops.basesetup.ResponseDTO;

import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolsScrapNoteResponseDTO {
	
	private Long id;
	
	private BranchResponseDTO branch;
	
	private ListOfValuesDetailsResponseDTO belongsTo;
	
	private DepartmentResponseDTO departement;
	
	private LocalTime time;
	
	private LocationMasterResponseDTO fromLocation;
	
	private LocationMasterResponseDTO toLocation;
	
	private EmployeeMasterResponseDetailsDTO preparedBy;
	
	private EmployeeMasterResponseDetailsDTO authorizedBy;
	
	private String productionApproval;
	
	private String qualityApproval;
	
	private String storeApproval;
	
	private String narration;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<MachineToolsScrapNoteDetailsResponseDTO> machineToolsScrapNoteDetailsResponseDTO;
	
	private List<MachineToolScrapNoteAttachmentResponseDTO> machineToolsScrapNoteAttachmentResponseDTO;




	

}
