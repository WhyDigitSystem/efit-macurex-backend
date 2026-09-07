package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderAmendmentDTO {

    private Long id;

    private Long customer;

    private Long branch;

    private String jobOrderNo;

    private LocalDate jobOrderDate;

    private String revisionNo;

    private LocalDate oldDeliveryDate;

    private LocalDate newDeliveryDate;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private List<JobOrderAmendmentDetailsDTO> jobOrderAmendmentDetails;

    // getters and setters
}
