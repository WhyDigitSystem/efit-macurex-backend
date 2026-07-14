package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalFgPartStockUpdateDTO {

	private Long id;
	private String routeCardNo;
	private String workOrderNo;
	private String fromLocation;
	private String toLocation;
	private String narration;
	private Long orgId;
	private String createdBy;
	private String part;

	private String partDesc;
	private BigDecimal qty;
	private String unit;
	private BigDecimal rate;
	private String status;
	private String branch;
	private String branchCode;
	private String finYear;

	List<FgStockUpdateDetailsDTO> FgStockUpdateDetailsDTO;

}
