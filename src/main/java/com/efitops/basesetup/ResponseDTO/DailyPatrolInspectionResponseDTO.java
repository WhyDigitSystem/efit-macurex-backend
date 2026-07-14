package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyPatrolInspectionResponseDTO {

	private String routeCardNo;

	private String partNo;

	private String partName;

	private String jobOrderNo;
	
	private List<DailyPatrolInspectionDetailsResponseDTO> dailyPatrolInspectionDetailsResponseDTO;

}
