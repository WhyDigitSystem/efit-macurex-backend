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
public class JobOrderAmendmentResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private CustomerDropdownResponseDTO customer;

    private BranchResponseDTO branch;

    private String jobOrderNo;

    private LocalDate jobOrderDate;

    private String revisionNo;

    private LocalDate oldDeliveryDate;

    private LocalDate newDeliveryDate;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String screenCode;

    private String screenName;

    private List<JobOrderAmendmentDetailsResponseDTO> jobOrderAmendmentDetails;
}