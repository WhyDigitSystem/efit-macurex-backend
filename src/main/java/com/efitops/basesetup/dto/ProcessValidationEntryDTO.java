package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessValidationEntryDTO {

    private Long id;

    private Long item;

    private Long customer;

    private Long processSheetNo;

    private String validationReason;

    private String detailsOfChanges;

    private Long controlPlan;

    private String characteristicsToBeMeasured;

    private String specification;

    private LocalDate dateImplemented;

    private String recommendedForProduction;

    private LocalDate dateOfNextValidation;

    private String resultsRemarks;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private Long branch;

    private List<ProcessValidationEntryDetailsDTO> details;
}