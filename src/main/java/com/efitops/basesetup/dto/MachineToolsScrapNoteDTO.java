package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolsScrapNoteDTO {

	private Long id;

	private Long branch;

	private Long belongsTo;

	private Long departement;

	private Long fromLocation;

	private Long toLocation;

	private Long preparedBy;

	private Long authorizedBy;

	private String productionApproval;

	private String qualityApproval;

	private String storeApproval;

	private String narration;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;

	private String time;

	private List<MachineToolsScrapNoteDetailsDTO> machineToolsScrapNoteDetailsDTO;

}
