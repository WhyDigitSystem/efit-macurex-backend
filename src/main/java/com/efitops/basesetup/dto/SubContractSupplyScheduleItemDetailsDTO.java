package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleItemDetailsDTO {

    private Long id;


    // =========================
    // Item Details
    // =========================

    private Long itemCode;

    private Long unit;

    private BigDecimal stock;

    private BigDecimal qty;

    private BigDecimal rate;


    // =========================
    // Schedule Details
    // =========================

    private List<SubContractSupplyScheduleDetailsDTO> scheduleDetails;
}