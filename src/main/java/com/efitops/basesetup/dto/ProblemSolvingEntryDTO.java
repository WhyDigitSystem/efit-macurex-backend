package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSolvingEntryDTO {

	private Long id;

	private String docId;

	private LocalDate docDate = LocalDate.now();

	private Long branch;

	private Long department;

	private String reference;

	private Long customer;

	private Long item;

	private String machineNo;

	private LocalDate mfgDate;

	private String defectDesciption;

	private Long teamMember1;

	private Long teamMember2;

	private String shortTeamAction;

	private LocalDate closeDate;

	private Long preparedBy;

	private String recognizeTheTeam;
	
	private Long orgId;
	
	private String financialYear;
	
	private String active;
	
	private String cancelRemarks;
	
	private String createdBy;
	
	private List<ProblemSolvingActionDetailsDTO> problemSolvingActionDetailsDTO;
	
	private List<ProblemSolvingOtherDetailsDTO> problemSolvingOtherDetailsDTO;
	
	private List<ProblemSolvingRootDetailsDTO> problemSolvingRootDetailsDTO;
}
