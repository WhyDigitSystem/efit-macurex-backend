package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class SalesDeliveryScheduleDTO {

    private Long id;

    private String dlvNo;

    private LocalDate dlvDate;

    private Long branchId;

    private String monthOfSchedule;

    private String belongsTo;

    private String monthYear;
    
    private String remarks;

    private Long customerId;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private Boolean active;

    private Boolean cancel;

    private String screenCode;

    private String screenName;
    
    private List<SalesDeliveryScheduleDetailsDTO> details;
    
    private List<SalesDeliverySchedulePlanDTO> deliverySchedule;
}