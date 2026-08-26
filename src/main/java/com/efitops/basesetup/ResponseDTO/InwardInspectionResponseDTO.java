package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionResponseDTO {

	// Basic Fields
	private Long id;
	private String docId;
	private LocalDate docDate;
	private String inwardType;
	private String mrinGrnNo;
	private LocalDate mrinGrnDate;
	private LocalTime timeOfInspection;
	private LocalTime grnTime;
	private LocalDate isoExpiaryDate;
	private String poPcJoNo;
	private String ppapSample;
	private String scheduleNo;
	private String supInvNo;
	private LocalDate supInvDt;
	private String considerations;
	private String disposalAction;
	private String result;
	private String notes;

	// Common Fields
	private String createdBy;
	private String updatedBy;
	private String active;
	private String cancel;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private String screenName;
	private String screenCode;

	private BranchResponseDTO branch;

	private SupplierResponseDTO supplierCode;

	private EmployeeMasterResponseDetailsDTO checkedBy;

	private EmployeeMasterResponseDetailsDTO approvedBy;

	private List<InwardInspectionDetailsResponseDTO> inwardInspectionDetailsResponseDTO;
	private List<InwardInspectionFileUploadDetailsResponseDTO> inwardInspectionFileUploadDetailsResponseDTO;
}