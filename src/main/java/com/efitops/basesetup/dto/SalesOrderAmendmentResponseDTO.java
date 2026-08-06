package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderAmendmentResponseDTO {

    private Long id;

    private Long branchId;
    private String branchName;

    private String soAmendmentNo;

    private String soNumber;

    private LocalDate amendmentDate;

    private String partyPoAmendmentNo;
    
    private LocalDate summary;

    private LocalDate salesOrderDate;

    private LocalDate partyPoAmendmentDate;

    private String poNo;

    private Integer revisionNo;

    private LocalDate poDate;

    private String remarks;

    private Boolean approved;

    private Boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private Boolean cancel;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;
    
    private List<SalesOrderAmendmentDetailsResponseDTO> salesOrderAmendmentDetails;

}