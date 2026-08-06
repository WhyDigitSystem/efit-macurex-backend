package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SalesDeliverySchedulePlanDTO {

    private Long id;

    // Header
    private Long salesDeliveryScheduleId;

    // Schedule Details Grid
    private Long salesDeliveryScheduleDetailsId;

    // Delivery Schedule Grid
    private Integer dayNo;

    private LocalDate deliveryDate;

    private Integer weekNo;

    private String dayName;

    private Double deliveryQty;

}
