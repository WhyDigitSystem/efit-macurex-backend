package com.efitops.basesetup.ResponseDTO;


import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private DepartmentResponseDTO department;

    private String belongsTo;

    private ItemResponse1DTO fgSfgItem;

    private Long bomId;

    private String timeOfIndent;

    private LocationMasterResponseDTO fromLocation;

    private String approvedByPM;

    private EmployeeDropdownResponseDTO preparedBy;

    private EmployeeDropdownResponseDTO authorisedBy;

    private String remarks;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<BulkIssueIndentDetailsResponseDTO> details;
}