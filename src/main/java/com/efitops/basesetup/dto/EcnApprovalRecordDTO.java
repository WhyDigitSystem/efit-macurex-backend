package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcnApprovalRecordDTO {
	private Long id;
	private String customer;
	private String partName;
	private String partNo;
	private String drawingNo;
	private String currentRevisionId;
	private LocalDate currentRevisionDate;
	private String oldRev;
	private String detailsOfRevision;
	private String reasonForRevision;
	private String remarks;
	private String preparedBy;
	private String departmentP;
	private String stageDrawingsModifiedBy;
	private String departmentS;
	private String checkedBy;
	private String departmentC;
	private String statusC;
	private String verifiedBy;
	private String departmentV;
	private String statusV;
	private String aprrovedBy;
	private String departmentA;
	private String statusA;
	private String documentFormateNo;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String finYear;
}
