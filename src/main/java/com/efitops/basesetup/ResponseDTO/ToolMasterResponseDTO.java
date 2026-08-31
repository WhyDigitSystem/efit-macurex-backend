package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterResponseDTO {

	private Long id;

	private BranchResponseDTO branch;

	private ListOfValuesDetailsResponseDTO type;

	private DepartmentResponseDTO department;

	private String toolNo;

	private String toolDescription;

	private String PMChecklistNo;

	private String toolCategory;

	private LocationMasterResponseDTO location;

	private String drawingNo;

	private String serialNo;

	private String manufacturedBy;

	private String section;

	private String status;

	private ListOfValuesDetailsResponseDTO madeIn;

	private CustomerResponse1DTO purchaseFrom;

	private ListOfValuesDetailsResponseDTO modeOfPurchase;

	private EmployeeDropdownResponseDTO toolIncharge;

	private String toolUsedFor;

	private CustomerResponse1DTO toolOwnership;

	private LocationMasterResponseDTO presentLocation;

	private BigDecimal toolCost;

	private String cavityNumber;

	private String remarks;

	private String toolName;

	private String image;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;

	private List<ToolMasterTechnicalInfoDetailsResponseDTO> toolMasterTechnicalInfoDetailsDTO;

	private List<ToolMasterSpareDetailsResponseDTO> toolMasterSpareDetailsDTO;

	private List<ToolMasterComponentOutPutDetailsResponseDTO> toolMasterComponentOutPutDetailsDTO;

	private List<ToolMasterMachineHistoryDetailsResponseDTO> toolMasterMachineHistoryDetailsDTO;

	private List<ToolMasterAttachementResponseDTO> toolMasterAttachementDTO;
}