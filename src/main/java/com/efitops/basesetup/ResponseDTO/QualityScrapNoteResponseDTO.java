package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityScrapNoteResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;

	private LocalTime time;

	private ListOfValuesDetailsResponseDTO belongsTo;

	private DepartmentResponseDTO department;

	private LocationMasterResponseDTO fromLocation;

	private LocationMasterResponseDTO toLocation;

	private EmployeeMasterDetailsReponseDTO preparedBy;

	private EmployeeMasterDetailsReponseDTO authorizedBy;

	private BigDecimal totalScrapValue;

	private String qualityApproval;

	private String narration;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<QualityScrapNoteDetailsResponseDTO> qualityScrapNoteDetailsResponseDTO;

}
