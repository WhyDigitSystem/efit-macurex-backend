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
public class DailyInspectionCumRejectionDataResponseDTO {
	private Long id;
	
	private String docId;
	
	private LocalDate docDate;

	private BranchResponseDTO branch;

	private ListOfValuesDetailsResponseDTO belongsTo;

	private EmployeeDropdownResponseDTO preparedBy;

	private LocationMasterResponseDTO fromLocation;

	private LocationMasterResponseDTO reworkLocation;

	private LocationMasterResponseDTO rejectionLocation;

	private LocationMasterResponseDTO scrapLocation;

	private LocationMasterResponseDTO toLocation;

	private String active;

	private long orgId;

	private String financialYear;

	private String createdBy;

	private String cancelRemarks;
	
	private List<DailyInspectionCumRejectionDetailsResponseDTO> dailyInspectionCumRejectionDetailsResponseDTO;

}
