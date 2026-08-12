package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesOrderAmendmentResponseDTO {

    private Long id;

    // Branch
    private Long branchId;
    private String branchName;

    // Document
    private String docId;
    private LocalDate docDate;

    // Sales Order
    private String salesOrderNumber;

    // Amendment Details
    private String partyPoAmendmentNo;
    private LocalDate salesOrderDate;
    private LocalDate partyPoAmendmentDate;

    // PO Details
    private String poNo;
    private Integer revisionNo;
    private LocalDate poDate;

    // Remarks
    private String remarks;

    // Common Details
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private Boolean active;
    private Boolean cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;

    // Grid
    private List<SalesOrderAmendmentDetailsResponseDTO> salesOrderAmendmentDetails;
}