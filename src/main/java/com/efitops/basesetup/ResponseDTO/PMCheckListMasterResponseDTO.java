package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PMCheckListMasterResponseDTO {

	private Long id;
	
	private String docId;
	
	private LocalDate docDate;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private String pmCheckListFor;

	private String pmCheckListNo;

	private ToolCategoryDetailResponseDTO toolCategory;

	private EmployeeMasterDetailsReponseDTO preparedBy;

	private EmployeeMasterDetailsReponseDTO approvedBy;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String financialYear;

	private String cancelRemarks;

	private String docId;

	private LocalDate docDate;

	private List<PMCheckListDetailsResponseDTO> pmCheckListDetailsResponseDTO;

}
