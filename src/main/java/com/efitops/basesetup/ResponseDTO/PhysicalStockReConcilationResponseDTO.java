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
public class PhysicalStockReConcilationResponseDTO {

	private Long id;

	private BranchResponseDTO branch;

	private ListOfValuesDetailsResponseDTO locationType;

	private String docId;

	private LocalDate docDate;

	private LocationMasterResponseDTO location;

	private String time;

	private String refNo;

	private LocalDate refDate;

	private String belongsTo;

	private EmployeeDropdownResponseDTO preparedBy;

	private String narration;

	private String approvedByPM;

	private Long orgId;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<PhysicalStockReConcilationDetailsResponseDTO>physicalStockReConcilationDetailsResponseDTO;

}
