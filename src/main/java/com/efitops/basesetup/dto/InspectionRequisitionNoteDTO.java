package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRequisitionNoteDTO {

    private Long id;

    private String requestedBy;

    private LocalDate date ;

    private String reasonForInspectionRequest;

    private String productCategory;

    private String requestComments;

    private LocalDate docDate;

    private String samplesSubmittedTo;

    private String partName;

    private String partNumber;

    private BigDecimal sampleQuantity;

    private String product;

    private String customer;

    private String supplier;

    private Long purchaseManager;

    private LocalDate purchaseManagerDate;

    private Long tdcManager;

    private LocalDate tdcManagerDate;

    private Long qualityManager;

    private LocalDate qualityManagerDate;

    private Long productionManager;

    private LocalDate productionManagerDate;

    private Long approvalRequestedBy;

    private Long approvedBy;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private Long branch;
}