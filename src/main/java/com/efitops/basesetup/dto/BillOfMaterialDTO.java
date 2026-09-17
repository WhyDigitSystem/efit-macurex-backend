package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfMaterialDTO {

	private Long id;

	private Long typeOfBom;

	private String typeOfItem;

	private Long fgItem;

	private String specifications;

	private String fillDetailsOf;

	private Long fillDetailsOfItem;

	private LocalDate wef;

	private String fgReferenceToProfit;

	private String manufacturing;

	private String remarks;

	// Common Fields
	private String createdBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private Long branch;

	// Child List
	private List<BillOfMaterialDetailsDTO> billOfMaterialDetailsDTO;
}