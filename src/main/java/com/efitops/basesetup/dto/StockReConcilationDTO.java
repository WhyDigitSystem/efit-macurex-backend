package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReConcilationDTO {
	private Long id;
	private String location;
	private String preparedBy;
	private String narration;
	private String createdBy;
	private String branch;
	private String branchCode;
	private String finYear;
	private Long orgId;

	private List<StockReConcilationDetailsDTO> stockReConcilationDetailsDTO;

}
