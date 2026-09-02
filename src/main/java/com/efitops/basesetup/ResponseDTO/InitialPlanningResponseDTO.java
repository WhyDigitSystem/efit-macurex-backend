package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.GradeMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningResponseDTO {
	private Long id;
	
	private ListOfValuesDetailsResponseDTO itemType;
	
	private String docId;
	
	private LocalDate docDate;

	private ItemResponse1DTO item;
	
	private GradeMasterResponseDTO item_grade;

	private String drawingNo;
	
	private CustomerResponse1DTO source;
	
	private String materialCharacteristics;
	
	private String samplingPlan;
	
	private String process;
	
	private String aesthetics;
	
	private String packingRequirements;
	
	private String others;
	
	private EmployeeDropdownResponseDTO preparedBy;
	
	private EmployeeDropdownResponseDTO approvedBy;
	
	private String approved;
	
	private Long orgId;

	private String financialYear;
	
	private String active;
	
	private String cancelRemarks;

	private String createdBy;
	
	private List<InitialPlanningDetailsResponseDTO>initialPlanningDetailsResponseDTO;

}
