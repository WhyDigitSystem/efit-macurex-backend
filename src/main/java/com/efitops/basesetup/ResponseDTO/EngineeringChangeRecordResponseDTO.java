package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EngineeringChangeRecordAttachmentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringChangeRecordResponseDTO {
	
	private Long id;

	private BranchResponseDTO branch;
	
	private String docId;
	
	private LocalDate docDate;

	private String fromDepartment;

	private String customerName;

	private EmployeeDropdownResponseDTO requestedBy;

	private String reasonForChange;

	private String productDescription;

	private String engineeringDrawingChange;

	private String bomChange;

	// REMARKS
	private String accepted;

	private String rejected;

	private EmployeeDropdownResponseDTO approvedBy;

	private String approved;

	// PRODUCT NO DETAILS
	private String customerProductNo;

	private String companyProductNo;

	// PART NO
	private String partNo;

	private String partDescription;

	// FOR TDC DEPARTMENT
	private String customerApproval;

	private String drawingWhichRequiredChange;

	private String documentWhichRequiredChange;

	private String active;

	private Long orgId;

	private String createdBy;

	private String cancelRemarks;
	
	private List<EngineeringChangeRecordAttachmentDTO> engineeringChangeRecordAttachmentDTO;
	
}
