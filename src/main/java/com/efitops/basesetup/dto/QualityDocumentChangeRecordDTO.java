package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityDocumentChangeRecordDTO {
	private Long id;
	private String name;
	private String designation;
	private String documentDescription;
	private String documentRefNo;
	private String currentRevisionStatus;
	private LocalDate recordDate;
	private String detailsOfChangeRequired;
	private String reasonForChange;
	private String changes;
	private String approvedBy;
	private LocalDate newDocumentReleaseDate;
	private String documentFormateNo;
	private String signature;
	private String narration;
	private Long orgId;

	private String branch;

	private String branchCode;

	private String createdBy;

	private String cancelRemarks;
	private String finYear;

}
