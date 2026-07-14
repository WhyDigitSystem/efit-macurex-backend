package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDTO {

	private Long id;

	private String indentType;

	private String customerName;

	private String customerCode;

	private String workOrderNo;

	private String department;

	private String fgPart;

	private String fgPartDesc;

	private Long fgQty;

	private String requestedBy;

	private String customerPoNo;

	private Long orgId;

	private String createdBy;

	private String branch;

	private String branchCode;

	private String finYear;
	
	private List<PurchaseIndentDetailsDTO> purchaseIndentDetailsDTO;

	private List<PurchaseIndentSummaryDTO> purchaseIndentSummaryDTO;

}
