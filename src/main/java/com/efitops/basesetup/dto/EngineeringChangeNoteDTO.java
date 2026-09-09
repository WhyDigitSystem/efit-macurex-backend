package com.efitops.basesetup.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineeringChangeNoteDTO {

    private Long id;
    private Long branch;
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
    private String customerApprovalRequiredOrNotRequired;
    
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
    private boolean active;
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private boolean cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    
    // Child Collections
    private List<RemarksDTO> remarksDTO;
    private List<ChangeRequiredDTO> changeRequiredDTO;
    private List<DocumentsChangesDTO> documentsChangesDTO;
    private List<DocumentsDTO> documentsDTO;
    private List<ProcessChangesDTO> processChangesDTO;
    private List<InspectionTestingDTO> inspectionTestingDTO;
}