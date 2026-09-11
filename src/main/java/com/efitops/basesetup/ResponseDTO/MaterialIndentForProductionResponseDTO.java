package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.Data;

@Data
public class MaterialIndentForProductionResponseDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;

	private DepartmentResponseDTO department;
	private String schOrderNo;
	private String belongsTo;
	private ItemMasterDetailsResponseImportDTO fgItem;
	private BigDecimal schQty;
	private LocalDate scheduledDate;
	private LocalTime indentTime;
	private LocationMasterResponseDTO toLocation;
	private LocationMasterResponseDTO fromLocation;

	private String createdBy;
	private String updatedBy;
	private String active;
	private String cancel;
	private String cancelRemarks;
	private String screenName;
	private String screenCode;
	private Long orgId;
	private String financialYear;
	private BranchResponseDTO branch;

	private List<MaterialIndentForProductionDetailsResponseDTO> materialIndentForProductionDetailsResponseDTO;
}
