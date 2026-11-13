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
public class AdvForStoresDTO {

    private Long id;

    private Long branch;

    private String belongsTo;

    private Long customer;

    private Long incomingPartNo;

    private Long bom;

    private String time;

    private Long preparedBy;

    private String remarks;

    // Common Fields

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<AdvForStoresDetailsDTO> advForStoresDetails;
}