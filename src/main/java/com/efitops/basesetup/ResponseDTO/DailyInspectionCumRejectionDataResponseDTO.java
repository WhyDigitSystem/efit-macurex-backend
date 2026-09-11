package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyInspectionCumRejectionDataResponseDTO {
	private Long id;

	private Long branch;

	private Long belongsTo;

	private Long preparedBy;

	private Long fromLocation;

	private Long reworkLocation;

	private Long rejectionLocation;

	private Long scrapLocation;

	private Long toLocation;

	private String active;

	private long orgId;

	private String financialYear;

	private String createdBy;

	private String cancelRemarks;
	
	private List<DailyInspectionCumRejectionDetailsResponseDTO> dailyInspectionCumRejectionDetailsResponseDTO;

}
