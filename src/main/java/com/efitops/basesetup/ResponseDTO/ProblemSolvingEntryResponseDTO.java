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
public class ProblemSolvingEntryResponseDTO {
	
	private Long id;

	private String docId;

	private LocalDate docDate;

	private BranchResponseDTO branch;

	private DepartmentResponseDTO department;

	private String reference;

	private CustomerResponse1DTO customer;

	private ItemResponse1DTO item;

	private String machineNo;

	private LocalDate mfgDate;

	private String defectDesciption;

	private EmployeeDropdownResponseDTO teamMember1;

	private EmployeeDropdownResponseDTO teamMember2;

	private String shortTeamAction;

	private LocalDate closeDate;

	private EmployeeDropdownResponseDTO preparedBy;

	private String recognizeTheTeam;
	
	private Long orgId;
	
	private String financialYear;
	
	private String active;
	
	private String cancelRemarks;
	
	private String createdBy;
	
	private List<ProblemSolvingRootDetailsResponseDTO>problemSolvingRootDetailsResponseDTO;
	
	private List<ProblemSolvingActionDetailsResponseDTO>problemSolvingActionDetailsResponseDTO;
	
	private List<ProblemSolvingOtherDetailsResponseDTO>problemSolvingOtherDetailsResponseDTO;

	
	
	

}
