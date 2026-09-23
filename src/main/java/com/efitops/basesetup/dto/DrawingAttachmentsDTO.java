package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DrawingAttachmentsDTO {
	
	
	    private Long id;

	    private Long typeOfItem;

	    private Long fgPartNo;

	    private String fgPartDescription;
	    
	    
	    private boolean active;

	    private Long orgId;

	    private String financialYear;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<DrawingAttachmentDetailDTO> drawingAttachmentDetailDTO;

}
