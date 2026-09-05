package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EngineeringDeviationAttachmentDTO;
import com.efitops.basesetup.entity.DepartmentVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringDeviationRequestResponseDTO {
	private Long id;

	private BranchResponseDTO branch;

	// DOCUMENT DETAILS
	private String docId;
	private LocalDate docDate;

	private DepartmentResponseDTO toDepartment;
	private String customerId;
//	private String customerName;
	private String productName;
	private BigDecimal quantityReceived;
	private String supplier;
	private EmployeeDropdownResponseDTO requestedBy;

	private EmployeeDropdownResponseDTO deviationRequistApprovedBy;
	private String invoiceNo;
	private String descriptionOfTheNC;
	private String reasonForDeviationRequest;
	private String actionOnNC;
	private String deviationPeriod;
	private EmployeeDropdownResponseDTO responsibleForName;
	private DepartmentResponseDTO department;

	private String reasonForChange;
	private String productDescription;
	private String engineeringDrawingChange;
	private String bomChange;

	// REMARKS
	private String accepted;
	private String rejected;

	private EmployeeDropdownResponseDTO approvedBy;

	private String approved;

	// PRODUCT NO DETAILS
	private String customerProductNo;
	private String companyProductNo;

	// PART NO
	private String partNo;
	private String partDescription;
	
    private String willTheNCAffectTheFit;

    private String willTheNCAffectTheForm;

    private String willTheNCAffectTheFunction;

    private String willTheNCAffectTheSafety;

    private String natureOfTheDeviationRequest;

    private String toBeIntimatedToCustomerAndActionOnCustomerFeedBack;

    private String note;

	// FOR TDC DEPARTMENT
	private String customerApproval;
	private String drawingWhichRequiredChange;
	private String documentWhichRequiredChange;
	
	private EmployeeDropdownResponseDTO productionMgr;
	private String productionMgrDisposition;
	private EmployeeDropdownResponseDTO qualityMgr;
	private String qualityMgrDisposition;
	private EmployeeDropdownResponseDTO tDCMgr;
	private String tdcMgrDisposition;
	private EmployeeDropdownResponseDTO directorTechnical;
	private String directorTechnicalDisposition;
	private EmployeeDropdownResponseDTO purMgr;
	private String purMgrDisposition;
	private String customerIntimationModeAndReference;
	private String customerFeedBack;
	private String customerFeedBackModeAndReference;
	private String Decision; 
	
	

	// STATUS DETAILS
	private String active;
	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	
	private List<EngineeringDeviationAttachmentDTO> engineeringDeviationAttachmentDTO;
	

}
