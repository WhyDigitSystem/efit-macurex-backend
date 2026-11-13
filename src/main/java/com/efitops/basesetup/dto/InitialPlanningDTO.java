package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialPlanningDTO {
    
	private Long id;
	
	private Long itemType;
	
//	private String docId;
//	
//	private LocalDate docDate;

	private Long item;
	
	private Long item_grade;

	private String drawingNo;
	
	private Long source;
	
	private String materialCharacteristics;
	
	private String samplingPlan;
	
	private String process;
	
	private String aesthetics;
	
	private String packingRequirements;
	
	private String others;
	
	private Long preparedBy;
	
	private Long approvedBy;
	
	private String approved;
	
	private Long orgId;

	private String financialYear;
	
	private boolean active;
	
	private String cancelRemarks;

	private String createdBy;
	
	private List<InitialPlanningDetailsDTO>initialPlanningDetailsDTO;


}
