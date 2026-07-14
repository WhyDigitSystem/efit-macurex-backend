package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessNonConformanceReportDTO {
	private Long id;
	private BigDecimal qtyAvailable;
	private BigDecimal qtyDefective;
	private String briefdescription;
	private String rootCause;
	private String disPosition;
	private String process;
	private String partType;
	private String responsibility;
	private String correctiveAction;
	private String partNo;
	private String verify;
	private String adequacy;
	private String created;
	private LocalDate targetDate;
	private LocalDate date;
	private LocalDate actualDateOfCompletion;
	private String effectivenessOfCorrective;
	private String drawingNo;
	private String partName;
	private String documentFormateNo;
	private String signature;
	private String narration;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String finYear;

}
