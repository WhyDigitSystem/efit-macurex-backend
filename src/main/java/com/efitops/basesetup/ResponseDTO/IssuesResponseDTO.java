package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuesResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private DepartmentResponseDTO department;

    private String belongsTo;

    private LocalTime time;

    private String refNo;

    private LocalDate refDate;

    private String indentNo;

    private LocationIssuesResponseDTO issueFrom;

    private LocationIssuesResponseDTO issueTo;

    private String narration;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String cancelRemarks;
    
    
    private List<IssuesDetailsResponseDTO> issuesDetails;
}