package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleDTO {

    private Long id;

    // =========================
    // Header Details
    // =========================

    private Long branch;

    private String belongsTo;

    private LocalDate schStartDate;

    private LocalDate schEndDate;


    // =========================
    // Party Details
    // =========================

    private Long customer;


    // =========================
    // Contract / Job Order
    // =========================

    private String contractNo;

    private LocalDate contractDate;

    private String jobOrderNo;


    // =========================
    // Prepared / Authorised
    // =========================

    private Long preparedBy;

    private Long authorisedBy;


    // =========================
    // Remarks
    // =========================

    private String remarks;


    // =========================
    // Common Fields
    // =========================

    private Long orgId;

    private String financialYear;

    private boolean active;

    private String cancelRemarks;

    private String createdBy;


    // =========================
    // Item Details
    // =========================

    private List<SubContractSupplyScheduleItemDetailsDTO> itemDetails;
}