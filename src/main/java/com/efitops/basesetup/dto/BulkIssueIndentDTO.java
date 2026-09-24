package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentDTO {

    private Long id;

    private Long branch;

    private Long department;

    private String belongsTo;

    private Long fgSfgItem;

    private Long bom;

    private String timeOfIndent;

    private Long fromLocation;

    private String approvedByPM;

    private Long preparedBy;

    private Long authorisedBy;

    private String remarks;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<BulkIssueIndentDetailsDTO> details;
}