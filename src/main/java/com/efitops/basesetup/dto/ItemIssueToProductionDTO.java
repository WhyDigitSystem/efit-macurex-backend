package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIssueToProductionDTO {

	private Long id;
	private String routeCardNo;
	private String workorder;
	private String fgItemId;
	private String fgItemDesc;
	private int fgQty;
	private String fromLocation;
	private String toLocation;
	private String remarks;
	private String preparedBy;
	private String createdBy;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;

	List<ItemIssueToProductionDetailsDTO> itemIssueToProductionDetailsDTO;
}
