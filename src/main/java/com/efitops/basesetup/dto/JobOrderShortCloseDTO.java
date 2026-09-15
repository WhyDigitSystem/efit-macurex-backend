package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderShortCloseDTO {

    private Long id;

    // Header
    private Long customer;

    private String jobOrderNo;

    private String referenceForSc;

    // Common Fields
    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long branch;

    private Long orgId;

    private String financialYear;

    // Child Details
    private List<JobOrderShortCloseDetailsDTO> jobOrderShortCloseDetails;
}