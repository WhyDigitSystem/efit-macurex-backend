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
public class ProcessValidationEntryResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private ItemResponse1DTO item;

    private CustomerResponse1DTO customer;

    private ProcessSheetCompRoutingResponseDetails processSheetNo;

    private String validationReason;

    private String detailsOfChanges;

    private ControlPlanResponseDetailsDTO controlPlan;

    private String characteristicsToBeMeasured;

    private String specification;

    private LocalDate dateImplemented;

    private String recommendedForProduction;

    private LocalDate dateOfNextValidation;

    private String resultsRemarks;

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

    private List<ProcessValidationEntryDetailsResponseDTO> details;
}