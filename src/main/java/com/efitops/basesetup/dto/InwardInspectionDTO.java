package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionDTO {

	private Long id;

	private String docId;

	private LocalDate docDate;

	private String inwardType;

	private Long supplierCode;

	private String mrinGrnNo;

	private LocalDate mrinGrnDate;

	private LocalDate isoExpiaryDate;

	private String poPcJoNo;

	private String ppapSample;

	private String scheduleNo;

	private String supInvNo;

	private LocalDate supInvDt;

	private String considerations;

	private String disposalAction;

	private Long checkedBy;

	private Long approvedBy;

	private String result;

	private String notes;

	// Common fields

	private String createdBy;

	private boolean active;

	private boolean cancel;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private List<InwardInspectionDetailsDTO> inwardInspectionDetailsDTO;

}
