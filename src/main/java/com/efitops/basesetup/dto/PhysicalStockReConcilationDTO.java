package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalStockReConcilationDTO {

	private Long id;

	private BranchVO branch;

	private Long locationType;

	private String docId;

	private LocalDate docDate;

	private Long location;

	private String time;

	private String refNo;

	private LocalDate refDate;

	private String belongsTo;

	private Long preparedBy;

	private String narration;

	private String approvedByPM;

	private Long orgId;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private String financialYear;
	
	private List<PhysicalStockReConcilationDetailsDTO>physicalStockReConcilationDetailsDTO;

}
