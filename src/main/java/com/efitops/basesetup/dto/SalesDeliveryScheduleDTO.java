package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDTO {

    private Long id;

//    private String dlvNo;
//
//    private LocalDate dlvDate;

    private Long branch;

    private String monthOfSchedule;

    private String belongsTo;

    private String monthYear;
    
    private String remarks;

    private Long customer;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String cancelRemarks;

    private Boolean active;
    
    private List<SalesDeliveryScheduleDetailsDTO> details;
    
}