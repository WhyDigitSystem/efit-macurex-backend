package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialStageInspectionDTO {

    private Long id;

    private Long branch;

    private String docId;

    private LocalDate docDate;

    private String shift;

    private Long itemCode;

    private String itemDescription;

    private String partyDrawingNo;

    private String drawingNo;

    private Long preparedBy;

    private LocalDate preparedDate;

    private Long gradeType;

    private Long partyId;

    private String partyName;

    private String workOrderNo;

    private String processSheetNo;

    // Summary

    private String reasonForInitialInspection;

    private String comment;

    private String recommendedForProduction;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    private List<InitialStageInspectionDetailDTO> initialStageInspectionDetailDTO;
    
}