package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentResponseDTO {
private Long id;
	
	private BranchResponseDTO branch;
	
	private String belongTo;

	private String docId;

	private LocalDate docDate;

	private DepartmentResponseDTO department;

	private String timeOfIndent;

	private String approvedByPM;

	private EmployeeDropdownResponseDTO preparedBy;

	private EmployeeDropdownResponseDTO authorizedBy;

	private String remarks;

	private Long orgId;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<InternalIndentDetailsResponseDTO>internalIndentDetailsResponseDTO;

}
