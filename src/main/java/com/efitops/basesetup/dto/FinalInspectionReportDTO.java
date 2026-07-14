package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalInspectionReportDTO {
	private Long id;
	private String routeCard;
	private String partName;
	private String partNo;
	private String untis;
	private String customer;
	private String poNo;
	private LocalDate inspectionDate;
	private String invoiceNo;
	private int lotQty;
	private int sampleQty;

	private String checkedBy;
	private String approvedBy;

	private String naration;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;
	private String documentFormatNo;

	private List<FirDimensionalInspectionDTO> firDimensionalInspectionDTO;

	private List<FirAppearanceInspectionDTO> firAppearanceInspectionDTO;

}
