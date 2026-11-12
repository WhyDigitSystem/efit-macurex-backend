package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CustomerResponseGstDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FgTransferSlipResponseDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;
	private String belongsTo;
	private String transferNo;
	private LocalDate transferDate;
	private BomFgResponseDTO bom;
	private String scheduleNo;
	private LocalDate scheduleDate;
	private BigDecimal scheduledQty;
	private BigDecimal qtyForInspection;
	private BigDecimal rate;
	private BigDecimal totalQty;
	private String remarks;

	private String createdBy;
	private String updatedBy;
	private String active;
	private String cancel;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private String screenName;
	private String screenCode;

	private LocationMasterResponseDTO fromLocation;
	private LocationMasterResponseDTO toLocation;
	private LocationMasterResponseDTO scrapLocation;
	private ItemMasterDetailsResponseImportDTO fgItem;
	private CustomerResponseGstDetailsDTO customer;
	private BranchResponseDTO branch;

	private List<FGTransferSlipDetailsResponseDTO> fgTransferSlipDetailsResponseDTO;
}