package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchPlanDTO {

	private Long id;
	private String routeCardEntry;
	private String customerName;
	private String customerCode;
	private String workOrderNo;
	private LocalDate scheduleDispatchDate;
	private String dispatchType;
	private String narration;
	
	private String createdBy;
	private Long orgId;
	
List<DispatchPlanDetailsDTO> dispatchPlanDetailsDTO;
	
}
