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

	// Header Fields
	private String docId;
	private LocalDate docDate;
	private String typeOfBom;
	private String typeOfItem;
	private Long fgSfgItemCode;
	private String fgSfgItemDescription;
	private Integer revisionNo;
	private String specifications;
	private String fillDetailsOf;
	private String fillDetailsOfItem;
	private LocalDate wef;
	private String fgReferenceToProfit;
	private String fmanbou;

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