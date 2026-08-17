package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIndentResponseDTO {

    private Long id;

    // Header
    private BranchResponseDTO branch;

    private String belongsTo;

    private String docId;
    private LocalDate docDate;

    private DepartmentResponseDTO department;

    private EmployeeResponseDTO preparedBy;

    private EmployeeResponseDTO byWhom;

    private boolean approved;

    // Summary
    private String remarks;

    // Common
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private String cancelRemarks;
    private boolean active;
    private boolean cancel;

    private String screenName;
    private String screenCode;

    // Grid
    private List<PurchaseIndentDetailsResponseDTO> details;

    // Attachments
    private List<PurchaseIndentAttachmentResponseDTO> attachments;

}