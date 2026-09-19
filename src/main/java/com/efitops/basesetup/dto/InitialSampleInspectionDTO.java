package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialSampleInspectionDTO {

    private Long id;

    private Long branch;

    private String docId;

    private LocalDate docDate;

    private Long department;

    private Long supplierId;

    private String supplierName;

    private String issueNo;

    private LocalDate issueDate;

    private Long itemCode;

    private String itemDescription;

    private String drawingNo;

    private Integer noOfSamples;

    private String batchNo;

    private BigDecimal sampleWeight;

    // =========================
    // SAMPLE SUMMARY
    // =========================

    private BigDecimal acceptedQty;

    private BigDecimal deviationOnAcceptedQty;

    private BigDecimal acceptedQtySegregation;

    private BigDecimal reworkQty;

    private BigDecimal totalAcceptedQty;

    private BigDecimal rejectedQty;

    private String decision;

    private String reasonForFinalInspection;

    private String comment;

    private Long preparedBy;

    private LocalDate preparedDate;

    // =========================
    // COMMON FIELDS
    // =========================

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    private List<InitialSampleInspectionDetailDTO> initialSampleInspectionDetailDTO;
}