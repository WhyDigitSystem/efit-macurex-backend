package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterResponseDTO;
import com.efitops.basesetup.dto.GradeMasterResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ControlPlanResponseDTO {
	
	    private Long id;
	 
	    private BranchResponseDTO branch;

	    private LocalDate revisionDate;

	    private ListOfValuesDetailsResponseDTO controlPlanType;

	    private String planNo;

	    private ItemResponse1DTO fgItemCode;

	    private String itemDescription;

	    private GradeMasterResponseDTO itemGrade;

	    private String itemSize;

	    private String processSheetNo;

	    private EmployeeMasterResponseDetailsDTO preparedBy;

	    private EmployeeMasterResponseDetailsDTO checkedBy;

	    private boolean approved;

	    private boolean active;

	    private Long orgId;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    private List<ControlPlanDetailResponseDTO> ControlPlanDetailResponseDTO;


}
