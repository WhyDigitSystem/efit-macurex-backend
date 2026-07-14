package com.efitops.basesetup.dto;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingMasterDTO {

private Long id;
	
	private String partNo;
	
	private String drawingNo;
	
	private String drawingRevNo;
	
	private LocalDate effDate;
	
	private String fgPartNo;
	
	private String fgPartName;
	
	private String createdBy;
	
	private String cancelRemarks;
	
	private Long orgId;
	private String branch;
	private String branchCode;
    private String finYear;
	
	private boolean active;

	
//	private List<DrawingMasterDetailsDTO> drawingMasterDetailsDTO;
//	
//	private List<DrawingMasterAttachmentsDTO> drawingMasterAttachmentsDTO;
}


