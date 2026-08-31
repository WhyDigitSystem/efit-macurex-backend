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

public class IssuesDTO {

    private Long id;

    private Long branch;

//   private String docId;
//
//  private LocalDate docDate;
  
  private String financialYear;
  
    private Long department;

    private String belongsTo;

    private LocalTime time;

    private String refNo;

    private LocalDate refDate;

    private String indentNo;

    private Long issueFrom;

    private Long issueTo;

    private String narration;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String cancelRemarks;
    
    
    private List<IssuesDetailsDTO> issuesDetails;
}