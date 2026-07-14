package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMaterialInspectionDTO {
	private Long id;
	private String materialType;
	private String grnNo;
	private String poNo;
	private String supplierName;
	private String dcInvNo;
	private String type;
	private String itemNo;
	private String material;
	private int qtyReceived;
	private int inspectedQuantity;
	private String documentFormatNo;
	private LocalDate date;

	// VisualInspection
	private String testCertificate;
	private int acceptedQty;
	private String rework;
	private String segregate;
	private String concessionallyAccepted;
	private int scrap;

	// Summary
	private String inspectedBy;
	private LocalDate inspectedDate;
	private String approvedBy;
	private LocalDate approvedDate;
	private String naration;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;

	List<IncomingMaterialInspectionDetailsDTO> incomingMaterialInspectionDetailsDTO;

	List<IncomingMaterialInspectionAppearanceDTO> incomingMaterialInspectionAppearanceDTO;
}
