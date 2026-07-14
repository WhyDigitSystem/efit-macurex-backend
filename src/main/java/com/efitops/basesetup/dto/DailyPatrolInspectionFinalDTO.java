package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPatrolInspectionFinalDTO {
	private String inspectionBy;
	private String inCharge;
	private String remarks;
	
}
