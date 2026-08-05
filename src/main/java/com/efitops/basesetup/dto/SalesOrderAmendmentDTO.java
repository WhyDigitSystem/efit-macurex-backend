package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAmendmentDTO {

    private Long id;
    private Long branch;
    private String soNumber;;
    private String salesOrderNumber;
    private LocalDate amendmentDate;
    private String partyPoAmendmentNo;
    private LocalDate salesOrderDate;
    private LocalDate partyPoAmendmentDate;
    private String poNo;
    private Integer revisionNo;
    private LocalDate poDate;
    private String remarks;
    private Boolean approved;
    private Boolean active;
    private String updatedBy;
    private boolean cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String createdBy;
    
	 

}