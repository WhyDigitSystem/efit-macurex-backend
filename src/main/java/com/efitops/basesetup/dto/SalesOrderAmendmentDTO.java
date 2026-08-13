package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAmendmentDTO {

    private Long id;

   
    private Long branch;
//    private String docId;
    private String salesOrderNumber;
//    private LocalDate docDate;
    private String partyPoAmendmentNo;
    private LocalDate salesOrderDate;
    private LocalDate partyPoAmendmentDate;
    private String poNo;
    private int revisionNo;
    private LocalDate poDate;
    private String remarks;
    private Boolean active;
    private Long orgId;
    private String createdBy;
//    private String updatedBy;
//    private boolean cancel;
    private String cancelRemarks;

    
    private List<SalesOrderAmendmentDetailsDTO> details;
}