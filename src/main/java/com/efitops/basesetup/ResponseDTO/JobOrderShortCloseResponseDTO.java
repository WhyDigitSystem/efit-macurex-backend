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
public class JobOrderShortCloseResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private JOShortCloseCustomerResponseDTO customer;

    private BranchResponseDTO branch;

    private String jobOrderNo;

    private String referenceForSc;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

	private List<JobOrderShortCloseDetailsResponseDTO>
            jobOrderShortCloseDetails;
}
