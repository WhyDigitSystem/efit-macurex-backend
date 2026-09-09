package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineeringChangeNoteResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String docId;
    private LocalDate docDate;
    private String fromDepartment;
    private String productName;
    private String customerName;
    private String productNo;
    private String customerPartNo;
    
    // Part Details
    private String partNo;
    private String partDescription;
    
    // Reason for Change
    private String customerRequirements;
    private String valueEngineering;
    private String qualityRequirements;
    private String purchaseRequirements;
    private String inCaseOthersPleaseMentionDetails;
    
    // Store and Logistics
    private String doesChangeChangeThePart;
    private String ifYesAction;
    private String partToBeReworked;
    private String partToBeScrapped;
    
    // What is the stock at
    private String stores;
    private String wIP;
    private String supplierIncludePo;
    private BigDecimal costOfStockPlusWIP;
    
    // Document Changes Required
    private String controlPlanReviewedAndUpdated;
    private String anyChangeInWorkInstructionSOP;
    
    // Stock and Design / Process Validation
    private String existingStockCanBeUsedTillStockIsExhausted;
    private String ifNoCostOfObselecence;
    private String processValidationRequired;
    private String validationDetail;
    private String validationReportToBeAttached;
    private String isThereanyBillOfMaterialChangeRequired;
    private String iFYESPleasemention;
    private LocalDate expectedDateOfCompletion;
    
    // Conclusion
    private String changesAccepted;
    private String changesRejected;
    private BigDecimal changesInvolvingCost;
    private String changesCanBeImplementedBy;
    private String conformationOnImplementationByQAD;
    private String customerApproval;
    
    // CFT Approval/Concurrence
    private String approvalByTDCMgr;
    private String acceptedByQADMgr;
    private String nonAcceptedQADReason;
    private String acceptedByPURMgr;
    private String nonAcceptedPURReason;
    private String acceptedbyPRODMgr;
    private String nonAcceptedPRODReason;
    private String acceptedByStoresMgr;
    private String nonAcceptedStoreReason;
    
    // Common Fields
    private String activeStatus;
    private String cancelStatus;
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    
    // Child Collections - Response DTOs
    private List<RemarksResponseDTO> remarksResponseDTO;
    private List<ChangeRequiredResponseDTO> changeRequiredResponseDTO;
    private List<DocumentsChangesResponseDTO> documentsChangesResponseDTO;
    private List<DocumentsResponseDTO> documentsResponseDTO;
    private List<ProcessChangesResponseDTO> processChangesResponseDTO;
    private List<InspectionTestingResponseDTO> inspectionTestingResponseDTO;
    private List<PdfAttachmentDrawingResponseDTO> pdfAttachmentDrawingResponseDTO;
    private List<PdfAttachmentBomResponseDTO> pdfAttachmentBomResponseDTO;
}