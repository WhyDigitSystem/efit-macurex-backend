package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDTO {

	private Long id;

	private String belongsTo;

	private Long fromLocation;

	private Long toLocation;

	private String reason;

	private String createdBy;

	private boolean active;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private Long toBranch;

	private String narration;

	private List<StockTransferDetailsDTO> stockTransferDetailsDTO;

}
