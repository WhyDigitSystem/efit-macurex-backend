package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialSampleInspectionResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private DepartmentResponseDTO department;

    private CustomerResponse1DTO supplierId;

    private String supplierName;

    private String issueNo;

    private LocalDate issueDate;

    private ItemMasterResponseDetailsDTO itemCode;

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

    private EmployeeMasterResponseDetailsDTO preparedBy;

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
    
    private List<InitialSampleInspectionDetailResponseDTO> initialSampleInspectionDetailResponseDTO;
    
    
    
   
    
}