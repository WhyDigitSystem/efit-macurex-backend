package com.efitops.basesetup.dto;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionIssueDTO {

    private Long id;
    private String belongsTo;
    private Long fgItem;
    private String indentNo;
    private LocalDate issueDate;
    private String schOrderNo;
    private String type;
    private Long fromLocation;
    private Long toLocation;
    private String narration;
    private Long orgId;
    private String financialYear;
    private Long branch;
    private String createdBy;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;
    private List<ProductionIssueDetailsDTO> itemDetails;
}