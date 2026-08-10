package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SalesDeliverySchedulePlanResponseDTO {

    private Long id;

    // Day Information
    private int dayNo;

    private LocalDate deliveryDate;

    private int weekNo;

    private String dayName;

    // Delivery Quantity
    private double deliveryQty;
}