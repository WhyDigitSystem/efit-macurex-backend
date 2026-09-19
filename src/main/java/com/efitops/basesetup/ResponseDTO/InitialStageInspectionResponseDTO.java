package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.GradeMasterResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InitialStageInspectionResponseDTO {
	
	
	    private Long id;
 
	    private BranchResponseDTO branch;

	    private String docId;

	    private LocalDate docDate;

	    private String shift;

	    private ItemMasterResponseDetailsDTO itemCode;

	    private String itemDescription;

	    private String partyDrawingNo;

	    private String drawingNo;

	    private EmployeeMasterResponseDetailsDTO preparedBy;

	    private LocalDate preparedDate;

	    private GradeMasterResponseDTO gradeType;

	    private CustomerResponse1DTO partyId;

	    private String partyName;

	    private String workOrderNo;

	    private String processSheetNo;

	    // Summary

	    private String reasonForInitialInspection;

	    private String comment;

	    private String recommendedForProduction;

	    private boolean active;

	    private Long orgId;

	    private String financialYear;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    private List<InitialStageInspectionDetailResponseDTO> initialStageInspectionDetailResponseDTO;
	    

}
