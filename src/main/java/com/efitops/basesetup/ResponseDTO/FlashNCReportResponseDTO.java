package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashNCReportResponseDTO {
	

	private Long id;

	private BranchResponseDTO branch;

	private ListOfValuesDetailsResponseDTO belongsTo;

	private ListOfValuesDetailsResponseDTO reference;

	private ListOfValuesDetailsResponseDTO fromDept;

	private ListOfValuesDetailsResponseDTO toDept;

	private String Description;

	private String drawingNo;

	private String mrinSCGRNNO;

	private LocalDate mrinDate;

	private BigDecimal occPercentage;

	private String invoiceNo;

	private String poNo;

	private CustomerResponse1DTO supplier;

	private String operationNo;

	private ItemResponse1DTO item;

	private BigDecimal lotQty;

	private BigDecimal sampleQty;

	private BigDecimal ncQty;

	private ListOfValuesDetailsResponseDTO disposal;

	private String defectSeen;

	private String problemStatus;

	private String actionOnDefectiveLot;

	private EmployeeDropdownResponseDTO inspectedBy;

	private ListOfValuesDetailsResponseDTO status;

	private String narration;

	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private String flashNCImageName;

	private List<FlashNCReportAttachmentResponseDTO> FlashNCReportAttachmentResponseDTO;

}
