package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDTO {

    private Long id;

    // Header
    private String docId;

    private Long branch;


    private String belongsTo;

    private LocalDate docDate;

    private Long department;

    private Long preparedBy;

    private Long byWhom;

    private boolean approved;

    // Summary
    private String remarks;

    // Common
    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean active;

    private boolean cancel;

    private String cancelRemarks;

    // Details Grid
    private List<PurchaseIndentDetailsDTO> details;

    // Attachments
    private List<PurchaseIndentAttachmentDTO> attachments;
   

}