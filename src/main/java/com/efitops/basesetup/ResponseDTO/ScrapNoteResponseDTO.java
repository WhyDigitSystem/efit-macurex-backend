package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteResponseDTO {
	private Long id;
	private String docId;
	private LocalDate docDate;
	private LocalTime time;
	private String belongsTo;
	private String schOrderNo;
	private BigDecimal totalScrapValue;
	private String pmApproval;
	private String qualityApproval;
	private String storeApproval;
	private String narration;
	private String createdBy;
	private String updatedBy;
	private String active;
	private String cancel;
	private String cancelRemarks;
	private String screenName;
	private String screenCode;
	private Long orgId;
	private String financialYear;

	private DepartmentResponseDTO department;
	private LocationMasterResponseDTO fromLocation;
	private LocationMasterResponseDTO toLocation;
	private ItemMasterDetailsResponseImportDTO fgPart;
	private BillOfMaterialDropdownResponseDTO bom;
	private ItemMasterDetailsResponseImportDTO scrapPart;
	private EmployeeMasterDetailsReponseDTO preparedBy;
	private EmployeeMasterDetailsReponseDTO authorisedBy;
	private ListOfValuesDetailsResponseDTO scrapId;
	private BranchResponseDTO branch;

	// Child Grids
	private List<ScrapNoteDetailsResponseDTO> scrapNoteDetails;
	private List<ScrapNoteReasonDetailsResponseDTO> scrapNoteReasonDetails;
}