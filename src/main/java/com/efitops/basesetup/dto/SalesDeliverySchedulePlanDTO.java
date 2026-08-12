package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SalesDeliverySchedulePlanDTO {

//    private Long id;

    // Delivery Schedule Grid
    private int dayNo;

    private LocalDate deliveryDate;

    private int weekNo;

    private String dayName;

    private double deliveryQty;

}
