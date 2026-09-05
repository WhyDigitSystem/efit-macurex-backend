package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringChangeRecordDTO {

	private Long id;

	private Long branch;

	private String fromDepartment;
	
	private String financialYear;

	private String customerName;

	private Long requestedBy;

	private String reasonForChange;

	private String productDescription;

	private String engineeringDrawingChange;

	private String bomChange;

	// REMARKS
	private String accepted;

	private String rejected;

	private Long approvedBy;

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

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String cancelRemarks;
	

}
