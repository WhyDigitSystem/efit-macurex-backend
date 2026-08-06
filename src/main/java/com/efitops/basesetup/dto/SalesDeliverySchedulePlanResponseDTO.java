package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SalesDeliverySchedulePlanResponseDTO {

    private Long id;

    // Header
    private Long salesDeliveryScheduleId;

    // Schedule Details Grid
    private Long salesDeliveryScheduleDetailsId;

    // Day Information
    private Integer dayNo;

    private LocalDate deliveryDate;

    private Integer weekNo;

    private String dayName;

    // Delivery Quantity
    private Double deliveryQty;

}