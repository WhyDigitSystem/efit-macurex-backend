package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssueDTO {

    private Long id;
    private String belongsTo;
    private LocalDate docDate;
    private Long fgItem;
    private String indentNo;
    private LocalDate issueDate;
    private String purchaseMaterialRef;
    private String type;
    private String refNo;
    private Long fromLocation;
    private Long toLocation;
    private String remarks;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;
    private String createdBy;
    private Long orgId;
    private String financialYear;
    private Long branch;

    private List<ProductionBulkIssueDetailsDTO> productionBulkIssueDetailsDTO;
}
