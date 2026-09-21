package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.DesignationResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListMasterResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private ListOfVlauesDetailsResponseDTO pmCheckListFor;

	private String pmCheckListNo;

	private ToolCategoryDetailResponseDTO toolCategory;

	private EmployeeMasterDetailsReponseDTO preparedBy;

	private EmployeeMasterDetailsReponseDTO approvedBy;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String financialYear;

	private String cancelRemarks;
	
	private List<PMCheckListDetailsResponseDTO> pmCheckListDetailsResponseDTO;

}
