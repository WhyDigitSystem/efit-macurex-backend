package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanDTO {
	
	
	    private Long id;

//	    private String docId;
//
//	    private LocalDate docDate;

	    private Long branch;

	    private LocalDate revisionDate;

	    private Long controlPlanType;

	    private String planNo;

	    private Long fgItemCode;

	    private String itemDescription;

	    private Long itemGrade;

	    private String itemSize;

	    private String processSheetNo;

	    private Long preparedBy;

	    private Long checkedBy;

	    private boolean approved;

	    private boolean active;

	    private Long orgId;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<ControlPlanDetailDTO> controlPlanDetailDTO;
	    
	    private List<ControlPlanParameterDTO> controlPlanParameterDTO;
	    
	    

	  
}
