package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;

import lombok.Data;

@Data
public class SalesDeliveryScheduleResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private String monthOfSchedule;

    private String belongsTo;

    private String monthYear;

    private String remarks;

    private CustomerDropdownResponseDTO customer;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;

    private String cancel;

    private String screenCode;

    private String screenName;

    private List<SalesDeliveryScheduleDetailsResponseDTO> details;
}