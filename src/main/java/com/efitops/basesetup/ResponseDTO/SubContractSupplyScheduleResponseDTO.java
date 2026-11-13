package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String belongsTo;

    private String docId;

    private LocalDate schStartDate;

    private LocalDate docDate;

    private LocalDate schEndDate;

    private CustomerDropdownResponseDTO customer;

    private String contractNo;

    private LocalDate contractDate;

    private String jobOrderNo;

    private EmployeeResponseDTO preparedBy;

    private EmployeeResponseDTO authorisedBy;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String createdBy;

    private String updatedBy;

    private String screenName;

    private String screenCode;

    private List<SubContractSupplyScheduleItemDetailsResponseDTO> itemDetails;
}