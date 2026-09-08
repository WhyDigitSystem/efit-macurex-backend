package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferResponseDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;
	private String belongsTo;
	private LocationMasterResponseDTO fromLocation;
	private LocationMasterResponseDTO toLocation;
	private String reason;
	private String createdBy;
	private String active;
	private String cancel;
	private String updatedBy;
	private String cancelRemarks;
	private String screenName;
	private String screenCode;
	private Long orgId;
	private String financialYear;
	private BranchResponseDTO branch;
	private BranchResponseDTO toBranch;
	private String narration;
	private List<StockTransferDetailsResponseDTO> stockTransferDetailsResponseDTO;
}