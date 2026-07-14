package com.efitops.basesetup.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolIssueEntryDTO {
	private Long id;
	private String instrumentName;
	private String instrumentDesc;
	private String seqCode;
	private String customerName;
	private String instrumentCode;
	private String instrumentRange;
	private String location;
	private Long leastCount;
	private String frequencyOfCalib;
	private Date calibratedDate;
	private Date dueForCalib;
	private String calibratedCertificateNo;
	private String preparedBy;
	private String apporvedBy;
	private String remarks;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean cancel;

}
