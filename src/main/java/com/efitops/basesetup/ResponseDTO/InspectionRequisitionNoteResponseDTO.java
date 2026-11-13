package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRequisitionNoteResponseDTO {

    private Long id;

    private String requestedBy;

    private LocalDate date;

    private String reasonForInspectionRequest;

    private String productCategory;

    private String requestComments;

    private String samplesSubmittedTo;

    private String partName;

    private String partNumber;

    private BigDecimal sampleQuantity;

    private String product;

    private String customer;

    private String supplier;

    private EmployeeDropdownResponseDTO purchaseManager;

    private LocalDate purchaseManagerDate;

    private EmployeeDropdownResponseDTO tdcManager;

    private LocalDate tdcManagerDate;

    private EmployeeDropdownResponseDTO qualityManager;

    private LocalDate qualityManagerDate;

    private EmployeeDropdownResponseDTO productionManager;

    private LocalDate productionManagerDate;

    private EmployeeDropdownResponseDTO approvalRequestedBy;

    private EmployeeDropdownResponseDTO approvedBy;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private BranchResponseDTO branch;
}